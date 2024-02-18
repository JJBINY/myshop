package jjbin.myshop.order.service;

import jjbin.myshop.order.domain.Order;
import jjbin.myshop.order.domain.OrderLineItem;
import jjbin.myshop.order.domain.OrderOption;
import jjbin.myshop.order.domain.OrderOptionGroup;
import jjbin.myshop.user.domain.User;

public class OrderMapper {

    public Order mapFrom(User user, Cart cart) {
        return Order.builder()
                .user(user)
                .status(Order.OrderStatus.PENDING)
                .orderLineItems(cart.getCartLineItems().stream().map(this::toOrderlineItem).toList())
                .discountCoupons(cart.getDiscountCoupons())
                .build();
    }

    private OrderLineItem toOrderlineItem(Cart.CartLineItem cartLineItem) {
        return OrderLineItem.builder()
                .product(cartLineItem.getProduct())
                .name(cartLineItem.getName())
                .discountCoupons(cartLineItem.getDiscountCoupons())
                .basicOrderOptionGroups(cartLineItem.getBasicOptionGroups().stream().map(this::toOptionGroup).toList())
                .optionGroups(cartLineItem.getOptionGroups().stream().map(this::toOptionGroup).toList())
                .build();
    }

    private OrderOptionGroup toOptionGroup(Cart.CartOptionGroup cartOptionGroup) {
        return OrderOptionGroup.builder()
                .name(cartOptionGroup.getName())
                .options(cartOptionGroup.getOptions().stream().map(this::toOption).toList())
                .build();
    }

    private OrderOption toOption(Cart.CartOption cartOption) {
        return OrderOption.builder()
                .name(cartOption.getName())
                .price(cartOption.getPrice())
                .build();
    }

}
