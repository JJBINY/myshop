package jjbin.myshop.discount.domain.policy;

import jjbin.myshop.generic.domain.Money;
import jjbin.myshop.generic.domain.Ratio;
import jjbin.myshop.order.domain.Order;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static jjbin.myshop.FixtureBuilder.*;

class DiscountPolicyTest {

    @Test
    @DisplayName("정액 할인 금액을 계산할 수 있다.")
    void 정액_할인_정책() throws Exception {
        // given
        Order order = anOrder().build();
        AmountDiscountPolicy sut = anAmountDiscountPolicy()
                .amount(Money.wons(1000))
                .build();

        // when
        Money amount = sut.calculateDiscountAmount(order.toDiscountContext());

        // then
        Assertions.assertThat(amount).isEqualTo(Money.wons(1000));
    }

    @Test
    @DisplayName("정률 할인 금액을 계산할 수 있다.")
    void 정률_할인_정책() throws Exception {
        // given
        Order order = anOrderWithPrice(Money.wons(10000)).build();
        RateDiscountPolicy sut = RateDiscountPolicy.builder()
                .rate(Ratio.valueOf(0.1))
                .build();

        // when
        Money amount = sut.calculateDiscountAmount(order.toDiscountContext());

        // then
        Assertions.assertThat(amount).isEqualTo(Money.wons(1000));
    }
}