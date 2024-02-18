package jjbin.myshop.discount.domain.context;

import jjbin.myshop.discount.domain.LineItemDiscountCoupon;
import jjbin.myshop.generic.domain.Money;
import jjbin.myshop.product.domain.Product;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;

@Getter
public class LineItemDiscountContext extends DiscountContext {

    private Product product;
    private String name;
    private int quantity;
    private List<OptionGroupDiscountContext> basicOptionGroupContexts = new ArrayList<>();
    private List<OptionGroupDiscountContext> optionGroupContexts = new ArrayList<>();
    private List<LineItemDiscountCoupon> discountCoupons = new ArrayList<>();

    @Builder
    public LineItemDiscountContext(Product product, String name, int quantity, List<OptionGroupDiscountContext> basicOptionGroupContexts, List<OptionGroupDiscountContext> optionGroupContexts, List<LineItemDiscountCoupon> discountCoupons) {
        this.product = product;
        this.name = name;
        this.quantity = quantity;
        this.basicOptionGroupContexts = basicOptionGroupContexts;
        this.optionGroupContexts = optionGroupContexts;
        this.discountCoupons.addAll(Objects.requireNonNullElse(discountCoupons, emptyList()));
    }

    public Product getProduct() {
        return product;
    }

    public List<LineItemDiscountCoupon> getDiscountCoupons() {
        return discountCoupons;
    }

    @Override
    protected void lockCascade() {
        for (OptionGroupDiscountContext optionGroup : optionGroupContexts) {
            optionGroup.lock();
        }
    }

    @Override
    public Money getPrice() {
        return Money.sum(getNonLockedContexts(basicOptionGroupContexts), OptionGroupDiscountContext::getPrice)
                .plus(Money.sum(getNonLockedContexts(optionGroupContexts), OptionGroupDiscountContext::getPrice));
    }

}
