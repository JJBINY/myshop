package jjbin.myshop.discount.domain;

import jjbin.myshop.discount.domain.context.DiscountContext;
import jjbin.myshop.generic.domain.Money;
import lombok.Getter;
import lombok.NonNull;

import static jjbin.myshop.discount.domain.DiscountCoupon.CouponStatus.USED;


public abstract class DiscountCoupon<T extends DiscountContext> {
    public enum CouponStatus{VALID,USED}

    @Getter
    protected Long id;
    protected DiscountCouponSpecification couponSpec;
    protected CouponStatus status;

    public DiscountCoupon(Long id, @NonNull DiscountCouponSpecification couponSpec, @NonNull CouponStatus status) {
        this.id = id;
        this.couponSpec = couponSpec;
        this.status = status;
    }

    public Money calculateDiscountAmount(@NonNull T context){
        validate(context);

        Money amount = couponSpec.calculateDiscountAmount(context);
        used();

        return amount;
    }

    private void validate(T context){
        if(!status.equals(CouponStatus.VALID)){
            throw new IllegalStateException("유효하지 않은 쿠폰입니다.");
        }

        if(context.isLocked()){
            throw new IllegalStateException("다른 할인 쿠폰과 중첩해서 적용할 수 없습니다.");
        }
    }

    public boolean isSatisfiedBy(T context){
        return couponSpec.canCalculateDiscountAmount(context);
    }

    private void used() {
        status = USED;
    }

    public boolean isExclusive(){
        return couponSpec.isExclusive();
    }
}
