package jjbin.myshop.order.domain;

import jjbin.myshop.discount.domain.context.OptionGroupDiscountContext;
import jjbin.myshop.generic.domain.Money;
import jjbin.myshop.product.domain.OptionGroup;
import lombok.Builder;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;


public class OrderOptionGroup {
    private Long id;
    private String name;
    private List<OrderOption> orderOptions = new ArrayList<>();

    @Builder
    public OrderOptionGroup(Long id, @NonNull String name, @NonNull List<OrderOption> options) {
        this.id = id;
        this.name = name;
        this.orderOptions.addAll(options);
    }

    public Money calculatePrice() {
        return Money.sum(orderOptions,OrderOption::getPrice);
    }

    public OptionGroup toOptionGroup() {
        return OptionGroup.builder()
                .name(name)
                .options(orderOptions.stream().map(OrderOption::toOption).toList())
                .build();
    }

    public OptionGroupDiscountContext toDiscountContext() {
        return OptionGroupDiscountContext.builder()
                .name(name)
                .optionContexts(orderOptions.stream().map(OrderOption::toDiscountContext).toList())
                .build();
    }

}
