package jjbin.myshop.order.domain;

import jjbin.myshop.generic.domain.Money;
import jjbin.myshop.order.service.Cart;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jjbin.myshop.order.domain.Order.OrderStatus.ORDERED;
import static jjbin.myshop.order.domain.Order.OrderStatus.PAYED;

public class Order {


    public enum OrderStatus {ORDERED, PAYED}

    private List<OrderLineItem> orderLineItems = new ArrayList<>();
    @Getter private OrderStatus orderStatus;

    @Builder
    private Order(List<OrderLineItem> orderLineItems, OrderStatus orderStatus) {
        this.orderLineItems = orderLineItems;
        this.orderStatus = orderStatus;
    }

    public static Order createOrder(Cart cart) {
        return Order.builder()
                .orderLineItems(cart.getCartLineItems().stream()
                        .map(Order::toOrderLineItem)
                        .toList())
                .orderStatus(null)
                .build();
    }

    private static OrderLineItem toOrderLineItem(Cart.CartLineItem cli) {
        return new OrderLineItem(cli.getProduct(), cli.getQuantity());
    }

    public void place() {
        orderLineItems.stream().forEach(OrderLineItem::placeOrder);
        orderStatus = ORDERED;
    }

    public void payed(){
        orderStatus = PAYED;
    }

    public Money calculateTotalPrice() {
        return orderLineItems.stream().map(OrderLineItem::calculatePrice).reduce(Money.ZERO, Money::plus);
    }
}
