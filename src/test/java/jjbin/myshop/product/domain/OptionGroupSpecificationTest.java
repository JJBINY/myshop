package jjbin.myshop.product.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static jjbin.myshop.FixtureBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;

class OptionGroupSpecificationTest {

    static Stream<Arguments> 유효성_검사() {
        OptionSpecification optionSpec = anOptionSpec().build();

        return Stream.of(
                Arguments.of("추가 옵션", false, List.of(optionSpec, optionSpec), true), //유효함
                Arguments.of("기본 옵션", false, List.of(optionSpec), false), // 이름 불일치
                Arguments.of("추가 옵션", false, List.of(anOptionSpec().name("없는 옵션").build()), false), // 만족하는 옵션 X
                Arguments.of("추가 옵션", true, List.of(optionSpec, optionSpec), false) //배타 선택 조건 위반
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("옵션 그룹 유효성을 확인할 수 있다.")
    void 유효성_검사(String name, boolean exclusive, List<OptionSpecification> optionSpecs, boolean result) throws Exception {
        // given
        OptionGroup optionGroup = anOptionGroup()
                .name("추가 옵션")
                .build();

        OptionGroupSpecification sut = anOptionGroupSpec()
                .name(name)
                .exclusive(exclusive)
                .optionSpecs(optionSpecs)
                .build();

        // when
        boolean satisfied = sut.isSatisfiedBy(optionGroup);

        // then
        assertThat(satisfied).isEqualTo(result);
    }

}