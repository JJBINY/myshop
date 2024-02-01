package jjbin.myshop.product.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;

public class Product {
    @Getter
    private final String name;
    private final String description;
    @Getter
    private int stock;
    private List<OptionGroupSpecification> optionGroupSpecs = new ArrayList<>();

    @Builder
    public Product(@NonNull String name, String description, int stock, @NonNull OptionGroupSpecification basicOptionGroupSpec, List<OptionGroupSpecification> optionGroupSpecs) {
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.optionGroupSpecs.add(basicOptionGroupSpec);
        this.optionGroupSpecs.addAll(requireNonNullElse(optionGroupSpecs, emptyList()));

        checkInvariant();
    }

    private void checkInvariant(){
        requireNonNull(name, "제품명은 필수입니다.");

        if(stock<0){
            throw new IllegalStateException("재고는 0 이상이어야 합니다.");
        }

        if(optionGroupSpecs.stream().filter(OptionGroupSpecification::isBaisc).count() != 1){
            throw new IllegalStateException("유일한 기본 옵션 그룹이 존재해야 합니다.");
        }
    }

    public boolean canReduceStock(int quantity) {
        return stock >= quantity;
    }

    public void reduceStock(int quantity) {
        if(quantity<=0){
            throw new IllegalArgumentException("감소할 수량은 양수여야 합니다.");
        }

        if (!canReduceStock(quantity)) {
            throw new IllegalStateException("재고가 부족합니다.");
        }

        stock -= quantity;

        assert stock >= 0;
    }

    public List<OptionGroupSpecification> getOptionGroupSpecs() {
        return List.copyOf(optionGroupSpecs);
    }


}
