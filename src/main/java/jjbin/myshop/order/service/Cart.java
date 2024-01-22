package jjbin.myshop.order.service;

import jjbin.myshop.product.domain.Product;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class Cart {
    private List<CartLineItem> cartLineItems = new ArrayList<>();

    public Cart(CartLineItem ... cartLineItems) {
        this.cartLineItems = Arrays.asList(cartLineItems);
    }

    @Getter
    public static class CartLineItem{
        private Product product;
        private int quantity;

        public CartLineItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }
    }
}
