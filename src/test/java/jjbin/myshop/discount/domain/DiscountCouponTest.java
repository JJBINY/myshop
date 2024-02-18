package jjbin.myshop.discount.domain;

import jjbin.myshop.discount.domain.context.LineItemDiscountContext;
import jjbin.myshop.discount.domain.context.OrderDiscountContext;
import jjbin.myshop.discount.domain.policy.AmountDiscountPolicy;
import jjbin.myshop.generic.domain.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static jjbin.myshop.FixtureBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiscountCouponTest {

    @Test
    @DisplayName("쿠폰 할인 금액을 계산할 수 있다.")
    void 할인_쿠폰_사용() throws Exception {
        // given
        OrderDiscountContext context = anOrder().build().toDiscountContext();
        AmountDiscountPolicy policy = anAmountDiscountPolicy()
                .amount(Money.wons(1000))
                .build();
        OrderDiscountCoupon sut = anOrderDiscountCouponWithPolicy(policy).build();

        // when
        Money amount = sut.calculateDiscountAmount(context);

        // then
        assertThat(amount).isEqualTo(Money.wons(1000));
    }
    
    @Test
    @DisplayName("동일한 대상으로 배타 적용 쿠폰들을 중첩 적용할 수 없다.")
    void 배타_적용_쿠폰() throws Exception {
        // given
        LineItemDiscountContext context = anOrderLineItem().build().toDiscountContext();
        AmountDiscountPolicy policy = anAmountDiscountPolicy()
                .amount(Money.wons(1000))
                .build();
        LineItemDiscountCoupon sut1 = anExclusiveLineItemDiscountCouponWithPolicy(policy).build();
        LineItemDiscountCoupon sut2 = anExclusiveLineItemDiscountCouponWithPolicy(policy).build();

        // when, then
        Money amount = sut1.calculateDiscountAmount(context);
        assertThat(amount).isEqualTo(Money.wons(1000));
        assertThatThrownBy(() -> sut2.calculateDiscountAmount(context))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("유효하지 않은 상태의 쿠폰은 사용할 수 없다.")
    void 유효하지_않은_쿠폰() throws Exception {
        // given
        OrderDiscountContext context = anOrder().build().toDiscountContext();
        AmountDiscountPolicy policy = anAmountDiscountPolicy()
                .amount(Money.wons(1000))
                .build();
        OrderDiscountCoupon sut = anOrderDiscountCouponWithPolicy(policy)
                .status(DiscountCoupon.CouponStatus.USED)
                .build();

        // when, then
        assertThatThrownBy(() -> sut.calculateDiscountAmount(context))
                .isInstanceOf(IllegalStateException.class);
    }
}