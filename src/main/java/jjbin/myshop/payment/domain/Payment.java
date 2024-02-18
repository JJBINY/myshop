package jjbin.myshop.payment.domain;

import jjbin.myshop.generic.domain.Money;
import jjbin.myshop.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.Objects;

public class Payment {

    public enum PaymentStatus{PENDING, COMPLETED, FAILED}
    private Long id;
    private User user;

    @Getter private PaymentStatus status;
    private final PaymentMethod method;
    @Getter private final Money orderAmount;
    @Getter private final Money discountAmount;
    @Getter private Money paymentAmount;

    @Builder
    public Payment(Long id, @NonNull User user, PaymentStatus status, @NonNull PaymentMethod method, @NonNull Money orderAmount, Money discountAmount) {
        this.id = id;
        this.user = user;
        this.status = status;
        this.method = method;
        this.orderAmount = orderAmount;
        this.discountAmount = Objects.requireNonNullElse(discountAmount, Money.ZERO);
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
}
