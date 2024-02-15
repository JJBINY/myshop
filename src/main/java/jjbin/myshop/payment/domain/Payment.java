package jjbin.myshop.payment.domain;

import jjbin.myshop.generic.domain.Money;
import jjbin.myshop.order.domain.Order;

public class Payment {

    private final Order order;
    private final PaymentMethod method;
    private final Money amount;

    public Payment(Order order, PaymentMethod method, Money amount) {
        this.order = order;
        this.method = method;
        this.amount = amount;
    }

    public void pay() {
        method.pay(amount);
        order.payed();
    }
}
