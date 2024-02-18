package jjbin.myshop.product.domain;

import lombok.Builder;

import java.util.List;
@Builder
public record LineItem(String name, List<OptionGroup> basicOptionGroups, List<OptionGroup> optionGroups) {
}
