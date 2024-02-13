package jjbin.myshop.discount.domain;

import jjbin.myshop.discount.domain.context.LineItemDiscountContext;
import lombok.Builder;
import lombok.NonNull;


public class LineItemDiscountCoupon extends DiscountCoupon<LineItemDiscountContext>{
    @Builder
    public LineItemDiscountCoupon(Long id, @NonNull DiscountCouponSpecification couponSpec, @NonNull CouponStatus status) {
        super(id, couponSpec, status);
    }
}
