package jjbin.myshop.product.domain;

import lombok.Builder;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;


public class OptionGroupSpecification {
    private Long id;
    private String name;
    private boolean basic;
    private boolean exclusive;
    private List<OptionSpecification> optionSpecs = new ArrayList<>();

    @Builder
    public OptionGroupSpecification(Long id, @NonNull String name, boolean basic, boolean exclusive, @NonNull List<OptionSpecification> optionSpecs) {
        this.id = id;
        this.name = name;
        this.exclusive = exclusive;
        this.basic = basic;
        this.optionSpecs.addAll(optionSpecs);

        checkInvariant();
    }

    private void checkInvariant(){
        requireNonNull(name, "옵션 그룹 이름은 필수입니다.");

        if(optionSpecs.isEmpty()){
            throw new IllegalStateException("옵션 그룹에 하나 이상의 옵션이 포함되어야 합니다.");
        }
    }

    public boolean isSatisfiedBy(@NonNull OptionGroup optionGroup) {
        if (!Objects.equals(name, optionGroup.name())) {
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

    boolean isBasic(){
        return basic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OptionGroupSpecification that)) return false;

        if (basic != that.basic) return false;
        if (exclusive != that.exclusive) return false;
        if (!name.equals(that.name)) return false;
        return optionSpecs.equals(that.optionSpecs);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (basic ? 1 : 0);
        result = 31 * result + (exclusive ? 1 : 0);
        result = 31 * result + optionSpecs.hashCode();
        return result;
    }
}
