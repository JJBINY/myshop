package jjbin.myshop.order.domain;

import jjbin.myshop.discount.domain.OrderDiscountCoupon;
import jjbin.myshop.discount.domain.context.OrderDiscountContext;
import jjbin.myshop.generic.domain.Money;
import jjbin.myshop.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;
import static jjbin.myshop.order.domain.Order.OrderStatus.ORDERED;
import static jjbin.myshop.order.domain.Order.OrderStatus.PAYED;

public class Order {
    public enum OrderStatus {PENDING, ORDERED, PAYED}

    @Getter
    private Long id;
    @Getter
    private User user;
    @Getter
    private OrderStatus status;
    private final List<OrderLineItem> orderLineItems = new ArrayList<>();
    private final List<OrderDiscountCoupon> discountCoupons = new ArrayList<>();

    @Builder
    public Order(Long id, @NonNull User user, @NonNull OrderStatus status, @NonNull List<OrderLineItem> orderLineItems, List<OrderDiscountCoupon> discountCoupons) {
        this.id = id;
        this.user = user;
        this.status = status;
        this.orderLineItems.addAll(orderLineItems);
        this.discountCoupons.addAll(requireNonNullElse(discountCoupons, emptyList()));

        checkInvariant();
    }

    private void checkInvariant() {
        if (orderLineItems.isEmpty()) {
            throw new IllegalStateException("주문 항목은 필수입니다.");
        }

        requireNonNull(status);
    }

    public void place() {
        validate();

        orderLineItems.stream().forEach(OrderLineItem::place);
        ordered();

        assert status == ORDERED;
    }

    private void validate() {
        if (orderLineItems.isEmpty()) {
            throw new IllegalStateException("주문 항목이 비어 있습니다.");
        }

        orderLineItems.forEach(OrderLineItem::validate);
    }

    private void ordered() {
        status = ORDERED;
    }

    public void payed() {
        status = PAYED;

        assert status == PAYED;
    }

    public Money calculateTotalPrice() {
        Money totalPrice = Money.sum(orderLineItems, OrderLineItem::calculatePrice);

        if (totalPrice.isLessThan(Money.ZERO)) {
            throw new IllegalStateException("주문 금액은 0원 이상이어야 합니다.");
        }
        return totalPrice;
    }

    public OrderDiscountContext toDiscountContext() {
        return OrderDiscountContext.builder()
                .lineItemContexts(orderLineItems.stream().map(OrderLineItem::toDiscountContext).toList())
                .discountCoupons(discountCoupons)
                .build();
    }

}
