package jjbin.myshop.product.domain;

import jjbin.myshop.FixtureBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    @Test
    @DisplayName("재고를 감소시킬 수 있다.")
    void 재고_감소() throws Exception {
        // given
        Product sut = FixtureBuilder.aProduct()
                .stock(10)
                .build();

        // when
        sut.reduceStock();
        
        // then
        assertThat(sut.getStock()).isEqualTo(9);
    }
    
    @Test
    @DisplayName("재고가 부족하면 재고를 감소시킬 수 없다.")
    void 재고_감소_실패() throws Exception {
        // given
        Product sut = FixtureBuilder.aProduct()
                .stock(0)
                .build();

        // when, then
        assertThatThrownBy(() -> sut.reduceStock())
                .isInstanceOf(IllegalStateException.class);
    }

}