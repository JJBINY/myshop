package jjbin.myshop.order.domain;

import jjbin.myshop.product.domain.Product;

public class OrderLineItem {
    private Product product;
    private int quantity;

    public OrderLineItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public void placeOrder(){
        product.reduceStock(quantity);
    }
}
