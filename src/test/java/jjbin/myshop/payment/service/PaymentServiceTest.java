package jjbin.myshop.payment.service;

import jjbin.myshop.discount.service.DiscountService;
import jjbin.myshop.order.domain.Order;
import jjbin.myshop.payment.domain.Cash;
import jjbin.myshop.payment.domain.Payment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static jjbin.myshop.FixtureBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;

class PaymentServiceTest {

    @Test
    @DisplayName("주문에 대한 결제를 할 수 있다.")
    void 주문_결제() throws Exception {
        // given
        Order order = anOrder().build();
        PaymentService sut = new PaymentService(new DiscountService(List.of(aLineItemDiscount().build())));

        // when
        Payment payment = sut.payOrder(order, new Cash(BASIC_OPTION_PRICE.times(ITEM_QUANTITY)));

        // then
        assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.PAYED);
        assertThat(payment.getStatus()).isEqualTo(Payment.PaymentStatus.COMPLETED);
    }
}