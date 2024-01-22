package jjbin.myshop.order.domain;

import jjbin.myshop.generic.domain.Money;
import jjbin.myshop.order.service.Cart;
import jjbin.myshop.product.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static jjbin.myshop.order.domain.Order.OrderStatus.ORDERED;
import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {

    @Test
    @DisplayName("주문을 할 수 있다.")
    void can_place_an_order() throws Exception {
        // given
        Product product = Product.builder()
                .name("name")
                .description("desc")
                .price(Money.ZERO)
                .stock(10)
                .build();

        Cart cart = new Cart(
                new Cart.CartLineItem(product, 3),
                new Cart.CartLineItem(product, 7));

        Order sut = Order.createOrder(cart);

        // when
        sut.place();

        // then
        assertThat(sut.getOrderStatus()).isEqualTo(ORDERED);
        assertThat(product.getStock()).isEqualTo(0);
    }

    @Test
    @DisplayName("재고가 부족하면 주문이 실패한다.")
    void an_order_will_fail_when_there_is_not_enough_stock() throws Exception {
        // given
        Product product = Product.builder()
                .name("name")
                .description("desc")
                .price(Money.ZERO)
                .stock(0)
                .build();

        Cart cart = new Cart(new Cart.CartLineItem(product, 1));
        Order sut = Order.createOrder(cart);

        // when, then
        Assertions.assertThatThrownBy(() -> sut.place())
                .isInstanceOf(IllegalStateException.class);
    }
}