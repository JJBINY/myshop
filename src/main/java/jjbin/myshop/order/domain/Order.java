package jjbin.myshop.order.domain;

import jjbin.myshop.generic.domain.Money;
import lombok.Builder;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static jjbin.myshop.order.domain.Order.OrderStatus.*;

public class Order {

    public enum OrderStatus {PENDING, ORDERED, PAYED}

    private List<OrderLineItem> orderLineItems = new ArrayList<>();
    private OrderStatus orderStatus;

    @Builder
    public Order(@NonNull List<OrderLineItem> orderLineItems, OrderStatus orderStatus) {
        this.orderLineItems.addAll(orderLineItems);
        this.orderStatus = Objects.requireNonNullElse(orderStatus, PENDING);

        checkInvariant();
    }

    private void checkInvariant(){
        if(orderLineItems.isEmpty()){
            throw new IllegalStateException("주문 항목은 필수입니다.");
        }

        requireNonNull(orderStatus);
    }

    public void place() {
        validate();

        orderLineItems.stream().forEach(OrderLineItem::place);
        orderStatus = ORDERED;

        assert isOrdered();
    }

    private void validate(){
        if (orderLineItems.isEmpty()) {
            throw new IllegalStateException("주문 항목이 비어 있습니다.");
        }

        orderLineItems.forEach(OrderLineItem::validate);
    }

    public void payed(){
        orderStatus = PAYED;

        assert isPayed();
    }

    public Money calculateTotalPrice() {
        Money totalPrice = Money.sum(orderLineItems, OrderLineItem::calculatePrice);

        if(totalPrice.isLessThan(Money.ZERO)){
            throw new IllegalStateException("주문 금액은 0원 이상이어야 합니다.");
        }
        return totalPrice;
    }


    public boolean isOrdered() {
        return orderStatus == ORDERED;
    }

    public boolean isPayed() {
        return orderStatus == PAYED;
    }

}
