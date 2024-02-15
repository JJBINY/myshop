package jjbin.myshop.order.domain;

import jjbin.myshop.generic.domain.Money;
import jjbin.myshop.product.domain.Product;
import lombok.Builder;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class OrderLineItem {
    private Product product;
    private String name;
    private int quantity;
    private List<OrderOptionGroup> orderOptionGroups = new ArrayList<>();

    @Builder
    public OrderLineItem(@NonNull Product product, @NonNull String name, int quantity, @NonNull List<OrderOptionGroup> optionGroups) {
        this.product = product;
        this.name = name;
        this.quantity = quantity;
        this.orderOptionGroups.addAll(optionGroups);

        checkInvariant();
    }

    private void checkInvariant() {
        requireNonNull(product);
        requireNonNull(name);

        if(quantity<=0){
            throw new IllegalStateException("주문 수량은 1개 이상이어야 합니다.");
        }

        if(orderOptionGroups.isEmpty()){
            throw new IllegalStateException("주문 옵션 그룹은 필수입니다.");
        }
    }

    public void place(){
        product.reduceStock(quantity);
    }

    public Money calculatePrice(){
        Money price = Money.sum(orderOptionGroups, OrderOptionGroup::calculatePrice).times(quantity);

        assert price.isGreaterThanOrEqual(Money.ZERO);
        return price;
    }

    void validate() {
        if (!Objects.equals(name, product.getName())) {
            throw new IllegalStateException("상품명이 변경되었습니다.");
        }

        if(!product.canReduceStock(quantity)){
            throw new IllegalStateException("상품 재고가 부족합니다.");
        }

        for (OrderOptionGroup group : orderOptionGroups) {
            group.validate(product.getOptionGroupSpecs());
        }
    }
}
