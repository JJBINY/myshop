package jjbin.myshop.payment.domain;

import jjbin.myshop.generic.domain.Money;
import lombok.Getter;

public class Cash implements PaymentMethod {
    @Getter
    private Money amount;

    public Cash(Money amount) {
        this.amount = amount;
    }

    @Override
    public void pay(Money amount) {
        if (this.amount.isLessThan(amount)) {
            throw new IllegalStateException("잔액이 부족합니다.");
        }
        this.amount = this.amount.minus(amount);
    }
}
