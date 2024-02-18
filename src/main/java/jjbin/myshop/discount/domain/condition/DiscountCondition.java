package jjbin.myshop.discount.domain.condition;

import jjbin.myshop.discount.domain.context.DiscountContext;

public interface DiscountCondition{
    boolean isSatisfiedBy(DiscountContext context);
}
