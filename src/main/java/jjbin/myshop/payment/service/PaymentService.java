package jjbin.myshop.payment.service;

import jjbin.myshop.discount.service.DiscountService;
import jjbin.myshop.order.domain.Order;
import jjbin.myshop.payment.domain.Payment;
import jjbin.myshop.payment.domain.PaymentMethod;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaymentService {

    private final DiscountService discountService;

    public Payment payOrder(Order order, PaymentMethod method){
        Payment payment = Payment.builder()
                .status(Payment.PaymentStatus.PENDING)
                .method(method)
                .orderAmount(order.calculateTotalPrice())
                .discountAmount(discountService.calculateOrderDiscountAmount(order.toDiscountContext()))
                .build();

        payment.pay();
        order.payed();
        return payment;
    }
}
