package jjbin.myshop.discount.domain;

import jjbin.myshop.discount.domain.condition.PriceDiscountCondition;
import jjbin.myshop.discount.domain.context.OrderDiscountContext;
import jjbin.myshop.generic.domain.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static jjbin.myshop.FixtureBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiscountCouponSpecificationTest {

    @Test
    @DisplayName("쿠폰 명세에 따라 할인 금액을 계산할 수 있다.")
    void 할인_금액_계산() throws Exception {
        // given
        OrderDiscountContext discountContext = anOrder().build().toDiscountContext();
        DiscountCouponSpecification sut = aDiscountCouponSpec()
                .policy(anAmountDiscountPolicy().amount(Money.wons(1000)).build())
                .build();

        // when
        Money amount = sut.calculateDiscountAmount(discountContext);

        // then
        assertThat(amount).isEqualTo(Money.wons(1000));
    }

    @Test
    @DisplayName("쿠폰 적용 조건을 만족하지 못하면, 할인 금액 계산에 실패한다.")
    void 쿠폰_적용_실패() throws Exception {
        // given
        OrderDiscountContext discountContext = anOrder().build().toDiscountContext();
        DiscountCouponSpecification sut = aDiscountCouponSpec()
                .policy(anAmountDiscountPolicy().amount(Money.wons(1000)).build())
                .conditions(List.of(new PriceDiscountCondition(Money.wons(999999999))))
                .build();

        // when, then
        assertThatThrownBy(() -> sut.calculateDiscountAmount(discountContext)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("배타 할인 쿠폰 적용시, 대상 할인 객체가 잠긴다.")
    void 객체_잠금() throws Exception {
        // given
        OrderDiscountContext discountContext = anOrder().build().toDiscountContext();
        DiscountCouponSpecification sut = aDiscountCouponSpec()
                .exclusive(true)
                .build();

        // when
        sut.calculateDiscountAmount(discountContext);

        // then
        assertThat(discountContext.isLocked()).isTrue();
    }


}