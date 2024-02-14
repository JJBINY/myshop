package jjbin.myshop.product.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;

public class Product {
    @Getter
    private Long id;
    @Getter
    private final String name;
    private final String description;
    @Getter
    private AtomicInteger stock;
    private List<OptionGroupSpecification> basicOptionGroupSpecs = new ArrayList<>();
    private List<OptionGroupSpecification> optionGroupSpecs = new ArrayList<>();

    @Builder
    public Product(Long id, @NonNull String name, String description, int stock, @NonNull List<OptionGroupSpecification> basicOptionGroupSpecs, List<OptionGroupSpecification> optionGroupSpecs) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = new AtomicInteger(stock);
        this.basicOptionGroupSpecs.addAll(basicOptionGroupSpecs);
        this.optionGroupSpecs.addAll(requireNonNullElse(optionGroupSpecs, emptyList()));

        checkInvariant();
    }

    private void checkInvariant() {
        requireNonNull(name, "제품명은 필수입니다.");

        if (basicOptionGroupSpecs.isEmpty()) {
            throw new IllegalStateException("기본 옵션 그룹은 필수입니다.");
        }

        if (basicOptionGroupSpecs.stream().filter(spec -> !spec.isBasic()).count() > 0) {
            throw new IllegalArgumentException("기본 옵션 그룹은 기본 선택이어야 합니다.");
        }

        if (stock.get() < 0) {
            throw new IllegalStateException("재고는 0 이상이어야 합니다.");
        }
    }

    public boolean canReduceStock() {
        return stock.get() > 0;
    }

    public void reduceStock() {
        if (!canReduceStock()) {
            throw new IllegalStateException("재고가 부족합니다.");
        }

        stock.decrementAndGet();
        assert stock.get() >= 0;
    }

    public List<OptionGroupSpecification> getOptionGroupSpecs() {
        return List.copyOf(optionGroupSpecs);
    }

    public boolean isSatisfiedBy(LineItem lineItem) {
        if (!Objects.equals(lineItem.name(), name)) {
            return false;
        }

        if (lineItem.basicOptionGroups().size() != basicOptionGroupSpecs.size()) {
            return false;
        }

        for (OptionGroupSpecification spec : basicOptionGroupSpecs) {
            if (!satisfied(lineItem.basicOptionGroups(), spec)) {
                return false;
            }
        }

        if(!lineItem.optionGroups().isEmpty()) {
            for (OptionGroupSpecification spec : optionGroupSpecs) {
                if (!satisfied(lineItem.optionGroups(), spec)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean satisfied(List<OptionGroup> optionGroups, OptionGroupSpecification spec) {
        for (OptionGroup optionGroup : optionGroups) {
            if (spec.isSatisfiedBy(optionGroup)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        if (!name.equals(product.name)) return false;
        if (!Objects.equals(description, product.description)) return false;
        if (!basicOptionGroupSpecs.equals(product.basicOptionGroupSpecs)) return false;
        return Objects.equals(optionGroupSpecs, product.optionGroupSpecs);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + basicOptionGroupSpecs.hashCode();
        result = 31 * result + (optionGroupSpecs != null ? optionGroupSpecs.hashCode() : 0);
        return result;
    }
}
