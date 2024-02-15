package jjbin.myshop.discount.domain;

import jjbin.myshop.discount.domain.context.LineItemDiscountContext;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;


public class LineItemDiscountCoupon extends DiscountCoupon<LineItemDiscountContext> {
    @Getter
    private Long id;

    @Builder
    public LineItemDiscountCoupon(Long id, @NonNull DiscountCouponSpecification couponSpec, @NonNull CouponStatus status) {
        super(couponSpec, status);
        this.id = id;
    }
}
