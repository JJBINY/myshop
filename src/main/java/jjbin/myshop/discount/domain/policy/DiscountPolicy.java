package jjbin.myshop.discount.domain.policy;

import jjbin.myshop.discount.domain.context.DiscountContext;
import jjbin.myshop.generic.domain.Money;
import lombok.NonNull;

public abstract class DiscountPolicy {

    public Money calculateDiscountAmount(@NonNull DiscountContext context) {
        Money discountAmount = getDiscountAmount(context);

        Money price = context.getPrice();
        if (discountAmount.isGreaterThan(price)) {
            discountAmount = price;
        }

        assert discountAmount.isLessThanOrEqual(price);
        assert discountAmount.isGreaterThanOrEqual(Money.ZERO);
        return discountAmount;
    }

    protected abstract Money getDiscountAmount(DiscountContext context);
}
