package jjbin.myshop.discount.domain.condition;

import jjbin.myshop.discount.domain.context.DiscountContext;
import jjbin.myshop.discount.domain.context.LineItemDiscountContext;
import jjbin.myshop.discount.domain.context.OrderDiscountContext;
import lombok.Builder;

public class QuantityDiscountCondition implements DiscountCondition {
    private Long id;
    private int quantity;

    public QuantityDiscountCondition(int quantity) {
        this.quantity = quantity;
    }

    @Builder
    public QuantityDiscountCondition(Long id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    @Override
    public boolean isSatisfiedBy(DiscountContext context) {
        if (context instanceof LineItemDiscountContext) {
            return ((LineItemDiscountContext) context).getQuantity() >= quantity;
        } else if (context instanceof OrderDiscountContext) {
            return ((OrderDiscountContext) context).getTotalQuantity() >= quantity;
        }
        return false;
    }
}
