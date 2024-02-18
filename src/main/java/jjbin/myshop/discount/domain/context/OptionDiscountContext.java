package jjbin.myshop.discount.domain.context;

import jjbin.myshop.generic.domain.Money;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OptionDiscountContext extends DiscountContext {

    private String name;
    private Money price;

    @Builder
    public OptionDiscountContext(String name, Money price) {
        this.name = name;
        this.price = price;
    }

    @Override
    protected void lockCascade() {}

    @Override
    public Money getPrice() {
        return price;
    }
}
