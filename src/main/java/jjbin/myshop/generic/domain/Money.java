package jjbin.myshop.generic.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
public class Money {
    public static Money ZERO = new Money(BigDecimal.ZERO);
    private final BigDecimal amount;

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public static Money wons(long amount){
        return new Money(BigDecimal.valueOf(amount));
    }

    public Money times(double x){
        return new Money(this.amount.multiply(BigDecimal.valueOf(x)));
    }

    public Money plus(Money amount) {
        return new Money(this.amount.add(amount.amount));
    }

    public Money minus(Money amount) {
        return new Money(this.amount.subtract(amount.amount));
    }

    public boolean isLessThan(Money other) {
        return amount.compareTo(other.amount) < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money money)) return false;

        return amount.compareTo(money.amount) == 0;
    }

    @Override
    public int hashCode() {
        return amount != null ? amount.hashCode() : 0;
    }

    @Override
    public String toString() {
        return amount + "원";
    }
}
