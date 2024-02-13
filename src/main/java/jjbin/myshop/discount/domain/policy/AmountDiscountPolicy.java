package jjbin.myshop.discount.domain.policy;

import jjbin.myshop.discount.domain.context.DiscountContext;
import jjbin.myshop.generic.domain.Money;
import lombok.Builder;
import lombok.NonNull;

public class AmountDiscountPolicy extends DiscountPolicy {

    private final Money amount;

    @Builder
    public AmountDiscountPolicy(@NonNull Money amount) {
        this.amount = amount;
    }

    @Override
    protected Money getDiscountAmount(DiscountContext context) {
        return amount;
    }

}
