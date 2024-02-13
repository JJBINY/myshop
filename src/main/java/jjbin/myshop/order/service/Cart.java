package jjbin.myshop.order.service;

import jjbin.myshop.discount.domain.LineItemDiscountCoupon;
import jjbin.myshop.discount.domain.OrderDiscountCoupon;
import jjbin.myshop.generic.domain.Money;
import jjbin.myshop.product.domain.Product;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNullElse;

@Data
public class Cart {
    private List<CartLineItem> cartLineItems = new ArrayList<>();

    private List<OrderDiscountCoupon> discountCoupons = new ArrayList<>();

    @Builder
    public Cart(@NonNull List<CartLineItem> cartLineItems, List<OrderDiscountCoupon> discountCoupons) {
        this.cartLineItems = cartLineItems;
        this.discountCoupons = requireNonNullElse(discountCoupons, emptyList());
    }

    @Data
    public static class CartLineItem {

        private Product product;
        private String name;
        private int quantity;
        private List<CartOptionGroup> basicOptionGroups = new ArrayList<>();
        private List<CartOptionGroup> optionGroups = new ArrayList<>();
        private List<LineItemDiscountCoupon> discountCoupons = new ArrayList<>();

        @Builder
        public CartLineItem(@NonNull Product product, @NonNull String name, int quantity, @NonNull List<CartOptionGroup> basicOptionGroups,
                            List<CartOptionGroup> optionGroups, List<LineItemDiscountCoupon> discountCoupons) {
            this.product = product;
            this.name = name;
            this.quantity = quantity;
            this.basicOptionGroups = basicOptionGroups;
            this.optionGroups = requireNonNullElse(optionGroups, emptyList());
            this.discountCoupons = requireNonNullElse(discountCoupons, emptyList());
        }
    }

    @Data
    public static class CartOptionGroup {
        private String name;
        private List<CartOption> options = new ArrayList<>();

        @Builder
        public CartOptionGroup(@NonNull String name, @NonNull List<CartOption> options) {
            this.name = name;
            this.options = options;
        }
    }

    @Data
    public static class CartOption {
        private String name;
        private Money price;

        @Builder
        public CartOption(@NonNull String name, @NonNull Money price) {
            this.name = name;
            this.price = price;
        }
    }
}
