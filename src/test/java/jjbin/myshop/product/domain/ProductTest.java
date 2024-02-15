package jjbin.myshop.product.domain;

import jjbin.myshop.generic.domain.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    @Test
    @DisplayName("재고를 수량만큼 감소시킬 수 있다.")
    void can_reduce_stock() throws Exception {
        // given
        Product sut = Product.builder()
                .name("name")
                .description("desc")
                .price(Money.ZERO)
                .stock(10)
                .build();

        // when
        sut.reduceStock(10);
        
        // then
        assertThat(sut.getStock()).isEqualTo(0);
    }
    
    @Test
    @DisplayName("재고가 충분하지 않으면 재고를 감소시킬 수 없다.")
    void can_not_reduce_stock_when_there_is_not_enough() throws Exception {
        // given
        Product sut = Product.builder()
                .name("name")
                .description("desc")
                .price(Money.ZERO)
                .stock(5)
                .build();

        // when, then
        assertThatThrownBy(() -> sut.reduceStock(10))
                .isInstanceOf(IllegalStateException.class);
    }
}