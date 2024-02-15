package jjbin.myshop.payment.domain;

import jjbin.myshop.generic.domain.Money;
import jjbin.myshop.order.domain.Order;
import jjbin.myshop.order.service.Cart;
import jjbin.myshop.product.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static jjbin.myshop.order.domain.Order.OrderStatus.PAYED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PaymentTest {

    @Test
    @DisplayName("현금으로 주문에 대한 결제를 할 수 있다.")
    void can_pay_for_an_order_in_cash() throws Exception {
        // given
        Product product = Product.builder()
                .name("name")
                .description("desc")
                .price(Money.wons(1000))
                .stock(10)
                .build();
        Cart cart = new Cart(
                new Cart.CartLineItem(product, 3),
                new Cart.CartLineItem(product, 7));
        Order order = Order.createOrder(cart);
        Cash cash = new Cash(Money.wons(10000));
        Payment sut = new Payment(order, cash, order.calculateTotalPrice());

        // when
        sut.pay();

        // then
        assertThat(cash.getAmount()).isEqualTo(Money.ZERO);
        assertThat(order.getOrderStatus()).isEqualTo(PAYED);
    }

    @Test
    @DisplayName("잔액이 부족하면 결제가 실패한다.")
    void payment_will_fail_when_insufficient_balance() throws Exception {
        // given
        Product product = Product.builder()
                .name("name")
                .description("desc")
                .price(Money.wons(1000))
                .stock(10)
                .build();
        Cart cart = new Cart(
                new Cart.CartLineItem(product, 3),
                new Cart.CartLineItem(product, 7));
        Order order = Order.createOrder(cart);
        Cash cash = new Cash(Money.wons(9999));
        Payment sut = new Payment(order, cash, order.calculateTotalPrice());

        // when, then
        assertThatThrownBy(() -> sut.pay())
                .isInstanceOf(IllegalStateException.class);
    }
}