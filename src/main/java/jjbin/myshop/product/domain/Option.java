package jjbin.myshop.product.domain;

import jjbin.myshop.generic.domain.Money;
import lombok.Builder;

@Builder
public record Option(String name, Money price) {
}
