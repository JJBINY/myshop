package jjbin.myshop.user.domain;

import lombok.Builder;
import lombok.NonNull;

import java.util.Objects;
import java.util.regex.Pattern;

public class User {
    private Long id;
    private String email;
    private String password;
    private String name;
    private final String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private final String passwordRegex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()\\-_+=]).{10,20}$";

    @Builder
    public User(Long id, @NonNull String email, @NonNull String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;

        validate(email, password);
    }

    private void validate(String email, String password) {
        if (!Pattern.matches(emailRegex, email)) {
            throw new IllegalStateException("이메일 형식이 올바르지 않습니다.");
        }

        if(!Pattern.matches(passwordRegex, password)){
            throw new IllegalStateException("비밀번호 형식이 올바르지 않습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;

        if (!Objects.equals(id, user.id)) return false;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
