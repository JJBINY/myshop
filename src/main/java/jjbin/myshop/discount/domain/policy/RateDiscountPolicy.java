package jjbin.myshop.discount.domain.policy;

import jjbin.myshop.discount.domain.context.DiscountContext;
import jjbin.myshop.generic.domain.Money;
import jjbin.myshop.generic.domain.Ratio;
import lombok.Builder;

public class RateDiscountPolicy extends DiscountPolicy {
    private final Ratio rate;

    @Builder
    public RateDiscountPolicy(Ratio rate) {
        this.rate = rate;
    }

    @Override
    protected Money getDiscountAmount(DiscountContext context) {
        return rate.of(context.getPrice());
    }

}
