package jjbin.myshop.discount.service;

import jjbin.myshop.discount.domain.*;
import jjbin.myshop.discount.domain.context.LineItemDiscountContext;
import jjbin.myshop.discount.domain.context.OrderDiscountContext;
import jjbin.myshop.generic.domain.Money;
import jjbin.myshop.product.domain.Product;
import lombok.RequiredArgsConstructor;

import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
@RequiredArgsConstructor
public class DiscountService {

    private final List<LineItemDiscount> lineItemDiscounts;

    public Money calculateOrderDiscountAmount(OrderDiscountContext orderContext) {
        return calculateTotalLineItemDiscountAmount(orderContext)
                .plus(calculateTotalCouponDiscountAmount(orderContext));
    }

    private Money calculateTotalLineItemDiscountAmount(OrderDiscountContext orderContext) {
        Map<Product, LineItemDiscount> lineItemDiscounts = this.lineItemDiscounts.stream().collect(toMap(LineItemDiscount::getProduct, identity())); //TODO repository를 통한 조회로 변경

        Money amount = Money.ZERO;
        for (LineItemDiscountContext lineItemContext : orderContext.getLineItemContexts()) {
            amount = amount.plus(lineItemDiscounts.get(lineItemContext.getProduct()).calculateLineItemDiscountAmount(lineItemContext));
        }
        return amount;
    }

    private Money calculateTotalCouponDiscountAmount(OrderDiscountContext orderContext) {
        return calculateTotalLineItemCouponDiscountAmount(orderContext)
                .plus(calculateTotalOrderCouponDiscountAmount(orderContext));
    }

    private Money calculateTotalLineItemCouponDiscountAmount(OrderDiscountContext orderContext) {

        Money amount = Money.ZERO;
        for (LineItemDiscountContext lineItemContext : orderContext.getLineItemContexts()) {
            Map<Boolean, List<LineItemDiscountCoupon>> coupons = lineItemContext.getDiscountCoupons().stream()
                    .collect(groupingBy(DiscountCoupon::isExclusive));
            amount = amount.plus(Money.sum(coupons.getOrDefault(true,emptyList()), coupon -> coupon.calculateDiscountAmount(lineItemContext))
                    .plus(Money.sum(coupons.getOrDefault(false,emptyList()), coupon -> coupon.calculateDiscountAmount(lineItemContext))));
        }
        return amount;
    }

    private Money calculateTotalOrderCouponDiscountAmount(OrderDiscountContext orderContext) {
        Map<Boolean, List<OrderDiscountCoupon>> coupons = orderContext.getDiscountCoupons().stream().collect(groupingBy(DiscountCoupon::isExclusive));
        return Money.sum(coupons.getOrDefault(true, emptyList()), coupon -> coupon.calculateDiscountAmount(orderContext))
                .plus(Money.sum(coupons.getOrDefault(false,emptyList()), coupon -> coupon.calculateDiscountAmount(orderContext)));
    }

}
