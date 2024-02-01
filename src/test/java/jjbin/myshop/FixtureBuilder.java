package jjbin.myshop;

import jjbin.myshop.generic.domain.Money;
import jjbin.myshop.order.domain.Order;
import jjbin.myshop.order.domain.OrderLineItem;
import jjbin.myshop.order.domain.OrderOption;
import jjbin.myshop.order.domain.OrderOptionGroup;
import jjbin.myshop.payment.domain.Cash;
import jjbin.myshop.payment.domain.Payment;
import jjbin.myshop.payment.domain.Payment.PaymentBuilder;
import jjbin.myshop.product.domain.*;
import jjbin.myshop.product.domain.Option.OptionBuilder;
import jjbin.myshop.product.domain.OptionGroup.OptionGroupBuilder;
import jjbin.myshop.product.domain.OptionGroupSpecification.OptionGroupSpecificationBuilder;
import jjbin.myshop.product.domain.OptionSpecification.OptionSpecificationBuilder;
import jjbin.myshop.product.domain.Product.ProductBuilder;

import java.util.List;

public class FixtureBuilder {

    private static String ITEM_NAME = "K8_키보드";
    private static int ITEM_QUANTITY = 1;
    private static String BASIC_OPTION_GROUP_NAME = "타입 선택";
    private static String OPTION_NAME = "텐키리스";
    private static Money BASIC_PRICE = Money.wons(100_000);

    public static ProductBuilder aProduct() {
        return Product.builder()
                .name(ITEM_NAME)
                .description("기계식 키보드")
                .stock(ITEM_QUANTITY)
                .basicOptionGroupSpec(anOptionGroupSpec()
                        .name(BASIC_OPTION_GROUP_NAME)
                        .optionSpecs(List.of(
                                anOptionSpec().build()))
                        .build())
                .optionGroupSpecs(List.of(
                        anOptionGroupSpec()
                                .basic(false)
                                .name("색상 선택")
                                .optionSpecs(List.of(
                                        anOptionSpec()
                                                .name("블랙")
                                                .price(Money.ZERO)
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
                .name(OPTION_NAME)
                .price(BASIC_PRICE);
    }

    public static OptionGroupBuilder anOptionGroup() {
        return OptionGroup.builder()
                .name(BASIC_OPTION_GROUP_NAME)
                .options(List.of(anOption().build()));
    }

    public static OptionBuilder anOption() {
        return Option.builder()
                .name(OPTION_NAME)
                .price(BASIC_PRICE);
    }

    public static Order.OrderBuilder anOrder() {
        return Order.builder()
                .orderStatus(Order.OrderStatus.PENDING)
                .orderLineItems(List.of(anOrderLineItem().build()));
    }

    public static OrderLineItem.OrderLineItemBuilder anOrderLineItem() {
        return OrderLineItem.builder()
                .product(aProduct().build())
                .name(ITEM_NAME)
                .quantity(ITEM_QUANTITY)
                .optionGroups(List.of(anOrderOptionGroup().build()));
    }

    public static OrderOptionGroup.OrderOptionGroupBuilder anOrderOptionGroup() {
        return OrderOptionGroup.builder()
                .name(BASIC_OPTION_GROUP_NAME)
                .options(List.of(anOrderOption().build()));
    }

    public static OrderOption.OrderOptionBuilder anOrderOption() {
        return OrderOption.builder()
                .name(OPTION_NAME)
                .price(BASIC_PRICE);
    }

    public static PaymentBuilder aPayment(){
        return Payment.builder()
                .order(anOrder().build())
                .amount(BASIC_PRICE)
                .method(new Cash(Money.wons(100_000)));
    }

}
