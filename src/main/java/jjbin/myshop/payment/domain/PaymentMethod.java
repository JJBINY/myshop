package jjbin.myshop.payment.domain;

import jjbin.myshop.generic.domain.Money;
import lombok.NonNull;

public interface PaymentMethod {
    void pay(@NonNull Money amount);
}
