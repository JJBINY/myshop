package jjbin.myshop.discount.domain.context;

import jjbin.myshop.generic.domain.Money;

import java.util.List;

public abstract class DiscountContext {
    private boolean locked;


    public boolean isLocked(){
        return locked;
    }
    public void lock(){
        locked = true;
        lockCascade();
    }

    protected <T extends DiscountContext> List<T> getNonLockedContexts(List<T> contexts){
        return contexts.stream().filter(context -> !context.isLocked()).toList();
    }

    protected abstract void lockCascade();

    public abstract Money getPrice();

}
