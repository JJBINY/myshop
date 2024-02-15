package jjbin.myshop.order.domain;

import jjbin.myshop.generic.domain.Money;
import jjbin.myshop.product.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static jjbin.myshop.FixtureBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {

    @Test
    @DisplayName("주문을 할 수 있다.")
    void 주문_성공() throws Exception {
        // given
        Order sut = anOrder().build();

        // when
        sut.place();

        // then
        assertThat(sut.isOrdered()).isTrue();
    }

    @Test
    @DisplayName("재고가 부족하면 주문이 실패한다.")
    void 주문_실패_재고_부족() throws Exception {
        // given
        Product product = aProduct()
                .stock(0)
                .build();
        OrderLineItem item = anOrderLineItem()
                .product(product)
                .quantity(10)
                .build();
        Order sut = anOrder()
                .orderLineItems(List.of(item))
                .build();

        // when, then
        Assertions.assertThatThrownBy(() -> sut.place())
                .isInstanceOf(IllegalStateException.class);
    }


    static Stream<Arguments> 주문_검증_실패() {
        return Stream.of(
                Arguments.of("초코파이", 1, "종류", "큰 박스", Money.wons(3000)),
                Arguments.of("빅파이", 100, "종류", "큰 박스", Money.wons(3000)),
                Arguments.of("빅파이", 1, "종류 선택", "큰 박스", Money.wons(3000)),
                Arguments.of("빅파이", 1, "종류", "작은 박스", Money.wons(3000)),
                Arguments.of("빅파이", 1, "종류", "큰박스", Money.wons(5000))
        );
    }

    @ParameterizedTest
    @MethodSource("주문_검증_실패")
    @DisplayName("주문과 상품 정보가 일치하지 않으면 주문이 실패한다.")
    void 주문_검증_실패(String itemName, int quantity, String optionGroupName, String optionName, Money price) throws Exception {
        // given
        Product product = aProduct()
                .name("빅파이")
                .stock(10)
                .basicOptionGroupSpec(anOptionGroupSpec()
                        .name("종류")
                        .optionSpecs(List.of(anOptionSpec()
                                .name("큰 박스")
                                .price(Money.wons(3000))
                                .build()))
                        .build())
                .build();
        OrderOption option = anOrderOption()
                .name(optionName)
                .price(price).build();
        OrderOptionGroup optionGroup = anOrderOptionGroup()
                .name(optionGroupName)
                .options(List.of(option))
                .build();
        OrderLineItem item = anOrderLineItem()
                .product(product)
                .name(itemName)
                .quantity(quantity)
                .optionGroups(List.of(optionGroup))
                .build();
        Order sut = anOrder()
                .orderLineItems(List.of(item))
                .build();

        // when, then
        assertThatThrownBy(() -> sut.place()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("주문 금액을 계산할 수 있다.")
    void 주문_금액_계산() throws Exception {
        // given
        OrderOption option = anOrderOption()
                .price(Money.wons(1000))
                .build();
        OrderOptionGroup optionGroup = anOrderOptionGroup()
                .options(List.of(option))
                .build();
        OrderLineItem item = anOrderLineItem()
                .quantity(10)
                .optionGroups(List.of(optionGroup))
                .build();
        Order sut = anOrder()
                .orderLineItems(List.of(item))
                .build();

        // when
        Money totalPrice = sut.calculateTotalPrice();

        // then
        assertThat(totalPrice).isEqualTo(Money.wons(10000));
    }

}