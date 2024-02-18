package jjbin.myshop.user.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static jjbin.myshop.FixtureBuilder.anUser;

class UserTest {

    public static Stream<Arguments> 유효성_검사() {
        return Stream.of(
                Arguments.of("my.@email.com", "password123!@#"),
                Arguments.of("my@email.com@", "password123!@#"),
                Arguments.of("@my@email.com", "password123!@#"),
                Arguments.of("my@email.com", "password"),
                Arguments.of("my@email.com", "password123"),
                Arguments.of("my@email.com", "password!@#"),
                Arguments.of("my@email.com", "pwd123!@#")
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("올바르지 않은 이메일과 비밀번호로 신규 사용자를 생성할 수 없다.")
    void 유효성_검사(String email, String password) throws Exception {
        // given
        // when, then
        Assertions.assertThatThrownBy(() -> anUser().email(email)
                .password(password)
                .build()).isInstanceOf(IllegalStateException.class);
    }
}