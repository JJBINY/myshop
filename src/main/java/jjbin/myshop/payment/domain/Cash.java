package jjbin.myshop.payment.domain;

import jjbin.myshop.generic.domain.Money;

public class Cash implements PaymentMethod {

    private Money amount;

    public Cash(Money amount) {
        this.amount = amount;
    }

    @Override
    public void pay(Money amount) {
        if (amount.isLessThan(Money.ZERO)) {
            throw new IllegalArgumentException("결제 금액은 0원 이상이어야 합니다.");
        }
        if (this.amount.isLessThan(amount)) {
            throw new IllegalStateException("잔액이 부족합니다.");
        }

        this.amount = this.amount.minus(amount);

        assert this.amount.isGreaterThanOrEqual(Money.ZERO);
    }

    public Money getAmount() {
        return amount;
    }
}
