package jjbin.myshop.discount.domain.condition;

import jjbin.myshop.discount.domain.context.DiscountContext;
import jjbin.myshop.discount.domain.context.LineItemDiscountContext;
import jjbin.myshop.discount.domain.context.OrderDiscountContext;
import jjbin.myshop.product.domain.Product;
import lombok.Builder;

public class ProductDiscountCondition implements DiscountCondition {
    private final Product product;

    @Builder
    public ProductDiscountCondition(Product product) {
        this.product = product;
    }

    @Override
    public boolean isSatisfiedBy(DiscountContext context) {
        if (context instanceof LineItemDiscountContext) {
            if (isSatisfiedBy(LineItemDiscountContext.class.cast(context))) {
                return true;
            }
        } else if (context instanceof OrderDiscountContext) {
            for (LineItemDiscountContext lineItemContext : OrderDiscountContext.class.cast(context).getLineItemContexts()) {
                if (isSatisfiedBy(lineItemContext)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isSatisfiedBy(LineItemDiscountContext context) {
        return context.getProduct().equals(product);
    }
}
