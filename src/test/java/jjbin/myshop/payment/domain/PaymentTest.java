package jjbin.myshop.payment.domain;

import jjbin.myshop.generic.domain.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static jjbin.myshop.FixtureBuilder.aPayment;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PaymentTest {

    @Test
    @DisplayName("현금으로 주문에 대한 결제를 할 수 있다.")
    void can_pay_for_an_order_in_cash() throws Exception {
        // given
        Cash cash = new Cash(Money.wons(10000));
        Money price = Money.wons(11000);
        Payment sut = aPayment()
                .orderAmount(price)
                .discountAmount(Money.wons(1000))
                .method(cash)
                .build();

        // when
        sut.pay();

        // then
        assertThat(cash.getAmount()).isEqualTo(Money.ZERO);
        assertThat(sut.getStatus()).isEqualTo(Payment.PaymentStatus.COMPLETED);
    }

    @Test
    @DisplayName("잔액이 부족하면 결제가 실패한다.")
    void payment_will_fail_when_insufficient_balance() throws Exception {
        // given
        Cash cash = new Cash(Money.wons(1000));
        Money price = Money.wons(11000);
        Payment sut = aPayment()
                .orderAmount(price)
                .discountAmount(Money.wons(1000))
                .method(cash)
                .build();


        // when, then
        assertThatThrownBy(() -> sut.pay()).isInstanceOf(IllegalStateException.class);
    }
}