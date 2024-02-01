package jjbin.myshop.product.domain;

import lombok.Builder;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;


public class OptionGroupSpecification {
    private String name;
    private boolean basic;
    private boolean exclusive;
    private List<OptionSpecification> optionSpecs = new ArrayList<>();

    @Builder
    public OptionGroupSpecification(@NonNull String name, boolean basic, boolean exclusive, @NonNull List<OptionSpecification> optionSpecs) {
        this.name = name;
        this.exclusive = exclusive;
        this.basic = basic;
        this.optionSpecs.addAll(optionSpecs);

        checkInvariant();
    }

    private void checkInvariant(){
        requireNonNull(name, "옵션 그룹 이름을 필수입니다.");

        if(optionSpecs.isEmpty()){
            throw new IllegalStateException("옵션 그룹에 하나 이상의 옵션이 포함되어야 합니다.");
        }
    }

    public boolean isSatisfiedBy(@NonNull OptionGroup optionGroup) {
        if (!name.equals(optionGroup.name())) {
            return false;
        }

        List<Option> satisfied = getSatisfiedOptions(optionGroup);
        if (satisfied.isEmpty()) {
            return false;
        }

        if (exclusive && satisfied.size() > 1) {
            return false;
        }

        return true;
    }

    private List<Option> getSatisfiedOptions(OptionGroup optionGroup) {
        assert !isNull(optionGroup);

        return optionSpecs.stream()
                .flatMap(optionSpec -> optionGroup.options().stream()
                        .filter(optionSpec::isSatisfiedBy))
                .toList();
    }

    boolean isBaisc(){
        return basic;
    }

}
