package jjbin.myshop.discount.domain;

import jjbin.myshop.discount.domain.condition.DiscountCondition;
import jjbin.myshop.discount.domain.context.LineItemDiscountContext;
import jjbin.myshop.discount.domain.policy.DiscountPolicy;
import jjbin.myshop.generic.domain.Money;
import jjbin.myshop.product.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;


public class LineItemDiscount {
    @Getter
    private Long id;
    @Getter
    private final Product product;
    private final DiscountPolicy policy;
    private final List<DiscountCondition> conditions = new ArrayList<>();

    @Builder
    public LineItemDiscount(Long id, @NonNull Product product, @NonNull DiscountPolicy policy, List<DiscountCondition> conditions) {
        this.id = id;
        this.product = product;
        this.policy = policy;
        this.conditions.addAll(Objects.requireNonNullElse(conditions, emptyList()));
    }


    public Money calculateLineItemDiscountAmount(@NonNull LineItemDiscountContext lineItemContext) {
        validate(lineItemContext);

        for (DiscountCondition condition : conditions) {
            if (!condition.isSatisfiedBy(lineItemContext)) {
                return Money.ZERO;
            }
        }

        return policy.calculateDiscountAmount(lineItemContext);
    }

    private void validate(LineItemDiscountContext lineItemContext) {
        if (!Objects.equals(lineItemContext.getProduct(), product)) {
            throw new IllegalArgumentException("상품이 일치하지 않습니다.");
        }
    }
}
