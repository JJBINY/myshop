package jjbin.myshop.discount.domain;

import jjbin.myshop.discount.domain.context.OrderDiscountContext;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;


public class OrderDiscountCoupon extends DiscountCoupon<OrderDiscountContext> {
    @Getter
    private Long id;

    @Builder
    public OrderDiscountCoupon(Long id, @NonNull DiscountCouponSpecification couponSpec, @NonNull CouponStatus status) {
        super(couponSpec, status);
        this.id = id;
    }
}
