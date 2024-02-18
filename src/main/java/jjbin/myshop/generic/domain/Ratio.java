package jjbin.myshop.generic.domain;

public class Ratio {
    private double rate;

    public static Ratio valueOf(double rate){
        return new Ratio(rate);
    }

    private Ratio(double rate) {
        this.rate = rate;
    }

    public Money of(Money amount){
        return amount.times(rate);
    }
}
