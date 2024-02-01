package jjbin.myshop.order.domain;

import jjbin.myshop.generic.domain.Money;
import jjbin.myshop.product.domain.OptionGroup;
import jjbin.myshop.product.domain.OptionGroupSpecification;
import lombok.Builder;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;


public class OrderOptionGroup {

    private String name;
    private List<OrderOption> orderOptions = new ArrayList<>();

    @Builder
    public OrderOptionGroup(@NonNull String name, @NonNull List<OrderOption> options) {
        this.name = name;
        this.orderOptions.addAll(options);
    }

    public Money calculatePrice() {
        return Money.sum(orderOptions,OrderOption::getPrice);
    }

    void validate(List<OptionGroupSpecification> optionGroupSpecs) {
        for (OptionGroupSpecification spec : optionGroupSpecs) {
            if (spec.isSatisfiedBy(this.toOptionGroup())) {
                return;
            }
        }

        throw new IllegalStateException("상품 옵션이 변경되었습니다.");
    }

    private OptionGroup toOptionGroup() {
        return OptionGroup.builder()
                .name(name)
                .options(orderOptions.stream().map(OrderOption::toOption).toList())
                .build();
    }
}
