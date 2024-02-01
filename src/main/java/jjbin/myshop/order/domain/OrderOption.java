package jjbin.myshop.order.domain;

import jjbin.myshop.generic.domain.Money;
import jjbin.myshop.product.domain.Option;
import lombok.Builder;
import lombok.NonNull;

public class OrderOption {
    private String name;
    private Money price;

    @Builder
    public OrderOption(@NonNull String name, @NonNull Money price) {
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
}
