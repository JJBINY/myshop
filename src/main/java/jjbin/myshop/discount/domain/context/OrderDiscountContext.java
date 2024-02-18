package jjbin.myshop.discount.domain.context;

import jjbin.myshop.discount.domain.OrderDiscountCoupon;
import jjbin.myshop.generic.domain.Money;
import lombok.Builder;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;

public class OrderDiscountContext extends DiscountContext {

    private List<LineItemDiscountContext> lineItemContexts = new ArrayList<>();
    private List<OrderDiscountCoupon> discountCoupons = new ArrayList<>();

    @Builder
    public OrderDiscountContext(@NonNull List<LineItemDiscountContext> lineItemContexts, List<OrderDiscountCoupon> discountCoupons) {
        this.lineItemContexts.addAll(lineItemContexts);
        this.discountCoupons.addAll(Objects.requireNonNullElse(discountCoupons, emptyList()));
    }

    public List<LineItemDiscountContext> getLineItemContexts() {
        return lineItemContexts;
    }

    public List<OrderDiscountCoupon> getDiscountCoupons() {
        return discountCoupons;
    }

    @Override
    protected void lockCascade() {
        for (LineItemDiscountContext lineItem : lineItemContexts) {
            lineItem.lock();
        }
    }

    @Override
    public Money getPrice() {
        return Money.sum(getNonLockedContexts(lineItemContexts), LineItemDiscountContext::getPrice);
    }

    public int getTotalQuantity() {
        return lineItemContexts.stream().mapToInt(LineItemDiscountContext::getQuantity).sum();
    }
}
