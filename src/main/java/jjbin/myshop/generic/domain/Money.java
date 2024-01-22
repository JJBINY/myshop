package jjbin.myshop.generic.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Money {
    public static Money ZERO = new Money(BigDecimal.ZERO);
    private BigDecimal amount;

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public static Money wons(long amount){
        return new Money(BigDecimal.valueOf(amount));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money money)) return false;

        return Objects.equals(amount, money.amount);
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
