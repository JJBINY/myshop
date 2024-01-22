package jjbin.myshop.product.domain;

import jjbin.myshop.generic.domain.Money;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Product {
    private String name;
    private String description;
    private Money price;
    private int stock;

    @Builder
    public Product(String name, String description, Money price, int stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public boolean canReduceStock(int quantity) {
        return stock>=quantity;
    }
    public void reduceStock(int quantity){
        if(!canReduceStock(quantity)){
            throw new IllegalStateException();
        }
        stock -= quantity;
    }
}
