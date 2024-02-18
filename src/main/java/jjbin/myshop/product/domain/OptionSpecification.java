package jjbin.myshop.product.domain;

import jjbin.myshop.generic.domain.Money;
import lombok.Builder;
import lombok.NonNull;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class OptionSpecification {
    private Long id;
    private String name;
    private Money price;

    @Builder
    public OptionSpecification(Long id, @NonNull String name, @NonNull Money price) {
        this.id = id;
        this.name = name;
        this.price = price;

        checkInvariant();
    }

    private void checkInvariant(){
        requireNonNull(name);
        requireNonNull(price);

        if(price.isLessThan(Money.ZERO)){
            throw new IllegalStateException("가격은 0원 이상이어야 합니다.");
        }
    }

    public boolean isSatisfiedBy(@NonNull Option option){
        return Objects.equals(name, option.name()) &&
                Objects.equals(price, option.price());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OptionSpecification that)) return false;

        if (!name.equals(that.name)) return false;
        return price.equals(that.price);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }
}
