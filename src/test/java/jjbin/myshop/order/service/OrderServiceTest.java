package jjbin.myshop.order.service;

import jjbin.myshop.order.domain.Order;
import jjbin.myshop.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static jjbin.myshop.FixtureBuilder.*;
import static jjbin.myshop.order.domain.Order.OrderStatus.ORDERED;

class OrderServiceTest {

    @Test
    @DisplayName("장바구니를 통해 주문을 할 수 있다.")
    void 주문_접수() throws Exception {
        // given
        Cart cart = Cart.builder()
                .cartLineItems(List.of(Cart.CartLineItem.builder()
                        .name(ITEM_NAME)
                        .product(aProduct().build())
                        .quantity(1)
                        .basicOptionGroups(List.of(Cart.CartOptionGroup.builder()
                                .name(BASIC_OPTION_GROUP_NAME)
                                .options(List.of(Cart.CartOption.builder()
                                        .name(BASIC_OPTION_NAME)
                                        .price(BASIC_OPTION_PRICE)
                                        .build()))
                                .build()))
                        .build()))
                .build();
        User user = anUser().build();
        OrderService sut = new OrderService(new OrderMapper());

        // when
        Order order = sut.placeOrder(user,cart);

        // then
        Assertions.assertThat(order.getStatus()).isEqualTo(ORDERED);
    }

}