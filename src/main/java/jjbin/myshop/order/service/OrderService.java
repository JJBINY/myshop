package jjbin.myshop.order.service;

import jjbin.myshop.order.domain.Order;
import jjbin.myshop.user.domain.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;

    public Order placeOrder(User user, Cart cart){
        Order order = orderMapper.mapFrom(user, cart);
        order.place();
        return order;
    }
}
