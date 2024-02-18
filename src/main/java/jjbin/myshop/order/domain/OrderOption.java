package jjbin.myshop.order.domain;

import jjbin.myshop.discount.domain.context.OptionDiscountContext;
import jjbin.myshop.generic.domain.Money;
import jjbin.myshop.product.domain.Option;
import lombok.Builder;
import lombok.NonNull;

public class OrderOption {
    private Long id;
    private String name;
    private Money price;

    @Builder
    public OrderOption(Long id, @NonNull String name, @NonNull Money price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    Option toOption(){
        return Option.builder()
                .name(name)
                .price(price)
                .build();
    }

    public Money getPrice() {
        return price;
    }

    public OptionDiscountContext toDiscountContext() {
        return OptionDiscountContext.builder()
                .name(name)
                .price(price)
                .build();
    }
}
