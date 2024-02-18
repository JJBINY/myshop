package jjbin.myshop.discount.domain;

import jjbin.myshop.discount.domain.context.OrderDiscountContext;
import jjbin.myshop.user.domain.User;
import lombok.Builder;
import lombok.Getter;


public class OrderDiscountCoupon extends DiscountCoupon<OrderDiscountContext> {
    @Getter
    private Long id;

    @Builder
    public OrderDiscountCoupon(Long id, User user, DiscountCouponSpecification couponSpec, CouponStatus status) {
        super(user, couponSpec, status);
        this.id = id;
    }
}
