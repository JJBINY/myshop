package jjbin.myshop.discount.service;

import jjbin.myshop.discount.domain.LineItemDiscount;
import jjbin.myshop.discount.domain.LineItemDiscountCoupon;
import jjbin.myshop.discount.domain.OrderDiscountCoupon;
import jjbin.myshop.discount.domain.context.OrderDiscountContext;
import jjbin.myshop.discount.domain.policy.AmountDiscountPolicy;
import jjbin.myshop.discount.domain.policy.RateDiscountPolicy;
import jjbin.myshop.generic.domain.Money;
import jjbin.myshop.generic.domain.Ratio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static jjbin.myshop.FixtureBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;

class DiscountServiceTest {

    @Test
    @DisplayName("총 주문 할인 금액을 계산할 수 있다.")
    void 주문_할인_금액_계산() throws Exception {
        // given
        LineItemDiscount lineItemDiscount = aLineItemDiscount().policy(new AmountDiscountPolicy(Money.wons(2000))).build();
        OrderDiscountCoupon orderCoupon = anOrderDiscountCouponWithPolicy(new AmountDiscountPolicy(Money.wons(3000))).build();
        LineItemDiscountCoupon itemCoupon = aLineItemDiscountCouponWithPolicy(new RateDiscountPolicy(Ratio.valueOf(0.1))).build();
        LineItemDiscountCoupon exItemCoupon = anExclusiveLineItemDiscountCouponWithPolicy(new RateDiscountPolicy(Ratio.valueOf(0.2))).build();

        OrderDiscountContext orderContext = anOrder()
                .discountCoupons(List.of(orderCoupon)) // 3000
                .orderLineItems(List.of(
                        anOrderLineItem() // 2000
                                .discountCoupons(List.of(itemCoupon))// 0.1
                                .build(),
                        anOrderLineItem() // 2000
                                .discountCoupons(List.of(exItemCoupon))// 0.2
                                .build()
                ))
                .build()
                .toDiscountContext();

        DiscountService sut = new DiscountService(List.of(lineItemDiscount));

        // when
        Money amount = sut.calculateOrderDiscountAmount(orderContext);

        // then

        assertThat(amount).isEqualTo(Money.wons(7000).plus(BASIC_OPTION_PRICE.times(0.3)));
    }

}