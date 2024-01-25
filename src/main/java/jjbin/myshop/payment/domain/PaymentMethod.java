package jjbin.myshop.payment.domain;

import jjbin.myshop.generic.domain.Money;

public interface PaymentMethod {
    void pay(Money amount);
}
