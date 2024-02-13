package jjbin.myshop.order.domain;

import jjbin.myshop.discount.domain.LineItemDiscountCoupon;
import jjbin.myshop.discount.domain.context.LineItemDiscountContext;
import jjbin.myshop.generic.domain.Money;
import jjbin.myshop.product.domain.LineItem;
import jjbin.myshop.product.domain.Product;
import lombok.Builder;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;

public class OrderLineItem {
    private Long id;
    private final Product product;
    private final String name;
    private final int quantity;
    private final List<OrderOptionGroup> basicOrderOptionGroups = new ArrayList<>();
    private final List<OrderOptionGroup> orderOptionGroups = new ArrayList<>();
    private final List<LineItemDiscountCoupon> discountCoupons = new ArrayList<>();


    @Builder
    public OrderLineItem(Long id, @NonNull Product product, @NonNull String name, int quantity, @NonNull List<OrderOptionGroup> basicOrderOptionGroups, List<OrderOptionGroup> optionGroups, List<LineItemDiscountCoupon> discountCoupons) {
        this.id = id;
        this.product = product;
        this.name = name;
        this.quantity = quantity;
        this.basicOrderOptionGroups.addAll(basicOrderOptionGroups);
        this.orderOptionGroups.addAll(requireNonNullElse(optionGroups, emptyList()));
        this.discountCoupons.addAll(requireNonNullElse(discountCoupons, emptyList()));

        checkInvariant();
    }

    private void checkInvariant() {
        requireNonNull(product, "주문 제품은 필수입니다.");
        requireNonNull(name, "주문 제품명은 필수입니다.");

        if(basicOrderOptionGroups.isEmpty()){
            throw new IllegalStateException("기본 주문 옵션 그룹은 필수입니다.");
        }
    }

    public void place(){
        product.reduceStock();
    }

    public Money calculatePrice(){
        Money price = Money.sum(basicOrderOptionGroups, OrderOptionGroup::calculatePrice)
                .plus(Money.sum(orderOptionGroups, OrderOptionGroup::calculatePrice))
                .times(quantity);

        assert price.isGreaterThanOrEqual(Money.ZERO);
        return price;
    }

    void validate() {

        if(!product.isSatisfiedBy(toLineItem())){
            throw new IllegalStateException("상품이 변경되었습니다.");
        }

        if(!product.canReduceStock()){
            throw new IllegalStateException("상품 재고가 부족합니다.");
        }

        for (OrderOptionGroup group : orderOptionGroups) {
            group.validate(product.getOptionGroupSpecs());
        }
    }

    public LineItem toLineItem(){
        return LineItem.builder()
                .name(name)
                .basicOptionGroups(basicOrderOptionGroups.stream().map(OrderOptionGroup::toOptionGroup).toList())
                .optionGroups(orderOptionGroups.stream().map(OrderOptionGroup::toOptionGroup).toList())
                .build();
    }

    public LineItemDiscountContext toDiscountContext() {
        return LineItemDiscountContext.builder()
                .product(product)
                .name(name)
                .quantity(quantity)
                .basicOptionGroupContexts(basicOrderOptionGroups.stream().map(OrderOptionGroup::toDiscountContext).toList())
                .optionGroupContexts(orderOptionGroups.stream().map(OrderOptionGroup::toDiscountContext).toList())
                .discountCoupons(discountCoupons)
                .build();

    }

}
