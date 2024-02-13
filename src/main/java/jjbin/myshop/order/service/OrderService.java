package jjbin.myshop.order.service;

import jjbin.myshop.order.domain.Order;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;

    public Order placeOrder(Cart cart){
        Order order = orderMapper.mapFrom(cart);
        order.place();
        return order;
    }
}
