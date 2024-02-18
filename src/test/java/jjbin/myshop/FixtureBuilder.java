package jjbin.myshop;

import jjbin.myshop.discount.domain.*;
import jjbin.myshop.discount.domain.DiscountCouponSpecification.DiscountCouponSpecificationBuilder;
import jjbin.myshop.discount.domain.LineItemDiscount.LineItemDiscountBuilder;
import jjbin.myshop.discount.domain.LineItemDiscountCoupon.LineItemDiscountCouponBuilder;
import jjbin.myshop.discount.domain.OrderDiscountCoupon.OrderDiscountCouponBuilder;
import jjbin.myshop.discount.domain.condition.PriceDiscountCondition;
import jjbin.myshop.discount.domain.condition.PriceDiscountCondition.PriceDiscountConditionBuilder;
import jjbin.myshop.discount.domain.policy.AmountDiscountPolicy;
import jjbin.myshop.discount.domain.policy.AmountDiscountPolicy.AmountDiscountPolicyBuilder;
import jjbin.myshop.discount.domain.policy.DiscountPolicy;
import jjbin.myshop.generic.domain.Money;
import jjbin.myshop.order.domain.Order;
import jjbin.myshop.order.domain.Order.OrderBuilder;
import jjbin.myshop.order.domain.OrderLineItem;
import jjbin.myshop.order.domain.OrderLineItem.OrderLineItemBuilder;
import jjbin.myshop.order.domain.OrderOption;
import jjbin.myshop.order.domain.OrderOption.OrderOptionBuilder;
import jjbin.myshop.order.domain.OrderOptionGroup;
import jjbin.myshop.order.domain.OrderOptionGroup.OrderOptionGroupBuilder;
import jjbin.myshop.payment.domain.Cash;
import jjbin.myshop.payment.domain.Payment;
import jjbin.myshop.payment.domain.Payment.PaymentBuilder;
import jjbin.myshop.product.domain.*;
import jjbin.myshop.product.domain.Option.OptionBuilder;
import jjbin.myshop.product.domain.OptionGroup.OptionGroupBuilder;
import jjbin.myshop.product.domain.OptionGroupSpecification.OptionGroupSpecificationBuilder;
import jjbin.myshop.product.domain.OptionSpecification.OptionSpecificationBuilder;
import jjbin.myshop.product.domain.Product.ProductBuilder;
import jjbin.myshop.user.domain.User;
import jjbin.myshop.user.domain.User.UserBuilder;

import java.util.List;

public class FixtureBuilder {

    public static String ITEM_NAME = "K8_키보드";
    public static int STOCK_QUANTITY = 100;
    public static int ITEM_QUANTITY = 1;
    public static String BASIC_OPTION_GROUP_NAME = "타입 선택";
    public static String BASIC_OPTION_NAME = "텐키리스";
    public static Money BASIC_OPTION_PRICE = Money.wons(100_000);
    public static String OPTION_GROUP_NAME = "색상 선택";
    public static String OPTION_NAME = "블랙";
    public static Money OPTION_PRICE = Money.ZERO;

    public static ProductBuilder aProduct() {
        return Product.builder()
                .name(ITEM_NAME)
                .description("기계식 키보드")
                .stock(STOCK_QUANTITY)
                .basicOptionGroupSpecs(List.of(anOptionGroupSpec()
                        .name(BASIC_OPTION_GROUP_NAME)
                        .basic(true)
                        .optionSpecs(List.of(
                                anOptionSpec().build()))
                        .build()))
                .optionGroupSpecs(List.of(
                        anOptionGroupSpec()
                                .basic(false)
                                .name(OPTION_GROUP_NAME)
                                .optionSpecs(List.of(
                                        anOptionSpec()
                                                .name(OPTION_NAME)
                                                .price(OPTION_PRICE)
                                                .build()))
                                .build()));

    }

    public static OptionGroupSpecificationBuilder anOptionGroupSpec() {
        return OptionGroupSpecification.builder()
                .name(BASIC_OPTION_GROUP_NAME)
                .basic(true)
                .exclusive(true)
                .optionSpecs(List.of(anOptionSpec().build()));
    }

    public static OptionSpecificationBuilder anOptionSpec() {
        return OptionSpecification.builder()
                .name(BASIC_OPTION_NAME)
                .price(BASIC_OPTION_PRICE);
    }

    public static OptionGroupBuilder anOptionGroup() {
        return OptionGroup.builder()
                .name(BASIC_OPTION_GROUP_NAME)
                .options(List.of(anOption().build()));
    }

    public static OptionBuilder anOption() {
        return Option.builder()
                .name(BASIC_OPTION_NAME)
                .price(BASIC_OPTION_PRICE);
    }

    public static OrderBuilder anOrder() {
        return Order.builder()
                .user(anUser().build())
                .status(Order.OrderStatus.PENDING)
                .orderLineItems(List.of(anOrderLineItem().build()));
    }

    public static OrderBuilder anOrderWithPrice(Money price){
        List<OrderOption> orderOptions = List.of(anOrderOption()
                .price(price)
                .build());
        List<OrderOptionGroup> orderOptionGroups = List.of(anOrderOptionGroup()
                .options(orderOptions)
                .build());
        List<OrderLineItem> orderLineItems = List.of(anOrderLineItem()
                .basicOrderOptionGroups(orderOptionGroups)
                .build());
        return anOrder()
                .orderLineItems(orderLineItems);
    }

    public static OrderLineItemBuilder anOrderLineItem() {
        return OrderLineItem.builder()
                .product(aProduct().build())
                .name(ITEM_NAME)
                .quantity(ITEM_QUANTITY)
                .basicOrderOptionGroups(List.of(anOrderOptionGroup().build()))
                .optionGroups(List.of(anOrderOptionGroup()
                        .name(OPTION_GROUP_NAME)
                        .options(List.of(anOrderOption()
                                .name(OPTION_NAME)
                                .price(OPTION_PRICE)
                                .build()))
                        .build()));
    }

    public static OrderOptionGroupBuilder anOrderOptionGroup() {
        return OrderOptionGroup.builder()
                .name(BASIC_OPTION_GROUP_NAME)
                .options(List.of(anOrderOption().build()));
    }

    public static OrderOptionBuilder anOrderOption() {
        return OrderOption.builder()
                .name(BASIC_OPTION_NAME)
                .price(BASIC_OPTION_PRICE);
    }

    public static DiscountCouponSpecificationBuilder aDiscountCouponSpec(){
        return DiscountCouponSpecification.builder()
                .policy(anAmountDiscountPolicy().build())
                .exclusive(false);
    }

    public static OrderDiscountCouponBuilder anOrderDiscountCoupon() {
        return OrderDiscountCoupon.builder()
                .status(DiscountCoupon.CouponStatus.VALID)
                .couponSpec(aDiscountCouponSpec().build());
    }

    public static OrderDiscountCouponBuilder anOrderDiscountCouponWithPolicy(DiscountPolicy policy) {
        return OrderDiscountCoupon.builder()
                .user(anUser().build())
                .status(DiscountCoupon.CouponStatus.VALID)
                .couponSpec(aDiscountCouponSpec()
                        .policy(policy)
                        .build());
    }

    public static LineItemDiscountCouponBuilder aLineItemDiscountCoupon() {
        return LineItemDiscountCoupon.builder()
                .user(anUser().build())
                .status(DiscountCoupon.CouponStatus.VALID)
                .couponSpec(aDiscountCouponSpec().build());
    }

    public static LineItemDiscountCouponBuilder aLineItemDiscountCouponWithPolicy(DiscountPolicy policy) {
        return aLineItemDiscountCoupon()
                .couponSpec(aDiscountCouponSpec()
                        .policy(policy)
                        .build());
    }

    public static LineItemDiscountCouponBuilder anExclusiveLineItemDiscountCouponWithPolicy(DiscountPolicy policy) {
        return aLineItemDiscountCoupon()
                .couponSpec(aDiscountCouponSpec()
                        .policy(policy)
                        .exclusive(true)
                        .build());
    }

    public static LineItemDiscountBuilder aLineItemDiscount() {
        return LineItemDiscount.builder()
                .product(aProduct().build())
                .policy(anAmountDiscountPolicy().build())
                .conditions(List.of(aPriceDiscountCondition().build()));
    }

    public static AmountDiscountPolicyBuilder anAmountDiscountPolicy() {
        return AmountDiscountPolicy.builder()
                .amount(Money.wons(10000));
    }

    public static PriceDiscountConditionBuilder aPriceDiscountCondition() {
        return PriceDiscountCondition.builder()
                .amount(BASIC_OPTION_PRICE);
    }


    public static PaymentBuilder aPayment() {
        return Payment.builder()
                .user(anUser().build())
                .status(Payment.PaymentStatus.PENDING)
                .orderAmount(BASIC_OPTION_PRICE)
                .discountAmount(Money.wons(1000))
                .method(new Cash(BASIC_OPTION_PRICE));
    }

    public static UserBuilder anUser(){
        return User.builder()
                .email("my@email.com")
                .password("password@12")
                .name("user");
    }

}
