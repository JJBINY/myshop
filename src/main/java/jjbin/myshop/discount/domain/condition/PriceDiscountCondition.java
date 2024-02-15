package jjbin.myshop.discount.domain.condition;

import jjbin.myshop.discount.domain.context.DiscountContext;
import jjbin.myshop.generic.domain.Money;
import lombok.Builder;

public class PriceDiscountCondition implements DiscountCondition {
    private Long id;
    private final Money amount;

    public PriceDiscountCondition(Money amount) {
        this.amount = amount;
    }

    @Builder
    public PriceDiscountCondition(Long id, Money amount) {
        this.id = id;
        this.amount = amount;
    }

    @Override
    public boolean isSatisfiedBy(DiscountContext context) {
        return context.getPrice().isGreaterThanOrEqual(amount);
    }
}
