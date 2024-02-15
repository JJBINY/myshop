package jjbin.myshop.product.domain;

import lombok.Builder;

import java.util.List;

@Builder
public record OptionGroup(String name, List<Option> options) {
}
