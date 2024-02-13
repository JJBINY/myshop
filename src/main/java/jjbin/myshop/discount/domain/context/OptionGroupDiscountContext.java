package jjbin.myshop.discount.domain.context;

import jjbin.myshop.generic.domain.Money;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

public class OptionGroupDiscountContext extends DiscountContext {

    private String name;
    private List<OptionDiscountContext> optionContexts = new ArrayList<>();

    @Builder
    public OptionGroupDiscountContext(String name, List<OptionDiscountContext> optionContexts) {
        this.name = name;
        this.optionContexts = optionContexts;
    }

    @Override
    protected void lockCascade() {
        for (OptionDiscountContext option : optionContexts) {
            option.lock();
        }
    }

    @Override
    public Money getPrice() {
        return Money.sum(getNonLockedContexts(optionContexts), OptionDiscountContext::getPrice);
    }


}
