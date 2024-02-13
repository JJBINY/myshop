package jjbin.myshop.discount.domain;

import jjbin.myshop.discount.domain.context.OrderDiscountContext;
import lombok.Builder;
import lombok.NonNull;


public class OrderDiscountCoupon extends DiscountCoupon<OrderDiscountContext> {

    @Builder
    public OrderDiscountCoupon(Long id, @NonNull DiscountCouponSpecification couponSpec, @NonNull CouponStatus status) {
        super(id, couponSpec, status);
    }
}
