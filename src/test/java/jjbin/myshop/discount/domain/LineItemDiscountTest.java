package jjbin.myshop.discount.domain;

import jjbin.myshop.generic.domain.Money;
import jjbin.myshop.order.domain.OrderLineItem;
import jjbin.myshop.product.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static jjbin.myshop.FixtureBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;

class LineItemDiscountTest {

    @Test
    @DisplayName("제품 할인 금액을 계산할 수 있다.")
    void 제품_할인() throws Exception {
        // given
        Product product = aProduct().build();
        OrderLineItem item = anOrderLineItem()
                .product(product)
                .build();

        LineItemDiscount sut = aLineItemDiscount()
                .product(product)
                .policy(anAmountDiscountPolicy()
                        .amount(Money.wons(1000))
//                        .conditions(emptyList())
                        .build())
                .build();

        // when
        Money amount = sut.calculateLineItemDiscountAmount(item.toDiscountContext());

        // then
        assertThat(amount).isEqualTo(Money.wons(1000));
    }

    @Test
    @DisplayName("제품 할인 금액은 제품 가격을 초과할 수 없다.")
    void 제품_할인_금액_제한() throws Exception {
        // given
        Product product = aProduct().build();
        Money itemPrice = Money.wons(10000);
        OrderLineItem item = anOrderLineItem()
                .product(product)
                .basicOrderOptionGroups(List.of(
                        anOrderOptionGroup()
                                .options(List.of(anOrderOption()
                                        .price(itemPrice)
                                        .build()))
                                .build()))
                .build();

        LineItemDiscount sut = aLineItemDiscount()
                .product(product)
                .policy(anAmountDiscountPolicy()
                        .amount(itemPrice.times(2))
                        .build())
                .build();

        // when
        Money amount = sut.calculateLineItemDiscountAmount(item.toDiscountContext());

        // then
        assertThat(amount.isLessThanOrEqual(itemPrice)).isTrue();
    }
}