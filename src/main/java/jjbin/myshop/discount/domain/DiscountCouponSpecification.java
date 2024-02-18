package jjbin.myshop.discount.domain;

import jjbin.myshop.discount.domain.condition.DiscountCondition;
import jjbin.myshop.discount.domain.context.DiscountContext;
import jjbin.myshop.discount.domain.policy.DiscountPolicy;
import jjbin.myshop.generic.domain.Money;
import lombok.Builder;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;


public class DiscountCouponSpecification {

    private Long id;
    private String name;
    private String description;
    private final DiscountPolicy policy;
    private final boolean exclusive;
    private final List<DiscountCondition> conditions = new ArrayList<>();

    @Builder
    public DiscountCouponSpecification(Long id, String name, String description, @NonNull DiscountPolicy policy, boolean exclusive, List<DiscountCondition> conditions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.policy = policy;
        this.exclusive = exclusive;
        this.conditions.addAll(Objects.requireNonNullElse(conditions, emptyList()));
    }

    public Money calculateDiscountAmount(@NonNull DiscountContext context) {
        if (!canCalculateDiscountAmount(context)) {
            throw new IllegalStateException("쿠폰 적용 조건을 확인해주세요.");
        }

        Money amount = policy.calculateDiscountAmount(context);

        if (isExclusive()) {
            context.lock();
        }

        return amount;
    }

    public boolean canCalculateDiscountAmount(@NonNull DiscountContext context) {
        for (DiscountCondition condition : conditions) {
            if (!condition.isSatisfiedBy(context)) {
                return false;
            }
        }
        return true;
    }

    public boolean isExclusive() {
        return exclusive;
    }

}
