package jjbin.myshop.payment.domain;

import jjbin.myshop.generic.domain.Money;
import lombok.Builder;
import lombok.Getter;

public class Payment {

    public enum PaymentStatus{PENDING, COMPLETED, FAILED}
    private Long id;

    @Getter private PaymentStatus status;
    private final PaymentMethod method;
    @Getter private final Money orderAmount;
    @Getter private final Money discountAmount;
    @Getter private Money paymentAmount;

    @Builder
    public Payment(Long id, PaymentStatus status,PaymentMethod method, Money orderAmount,Money discountAmount) {
        this.id = id;
        this.status = status;
        this.method = method;
        this.orderAmount = orderAmount;
        this.discountAmount = discountAmount;
    }

    public void pay() {
        paymentAmount = calculatePaymentAmount();
        failed();
        method.pay(paymentAmount);
        completed();

        assert status == PaymentStatus.COMPLETED;
    }


    private Money calculatePaymentAmount() {
        Money amount = orderAmount.minus(discountAmount);

        assert amount.isGreaterThanOrEqual(Money.ZERO);
        return amount;
    }

    private void completed(){
        status = PaymentStatus.COMPLETED;
    }

    private void failed(){
        status = PaymentStatus.FAILED;
    }

    public PaymentStatus getStatus() {
        return status;
    }
}
