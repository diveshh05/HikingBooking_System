package service;

import model.Trail;

public class PriceCalculator {

    public static final double DISCOUNT_SMALL = 0.10;
    public static final double DISCOUNT_LARGE = 0.15;

    public static double getDiscountRate(int groupSize){
        if(groupSize >=8){
            return DISCOUNT_LARGE;
        }
        if(groupSize >=4){
            return DISCOUNT_SMALL;
        }
        return 0.0;
    }

    public static double calculateGroupPrice(Trail trail, int groupSize) {
        if (groupSize <= 0) {
            throw new IllegalArgumentException("Group size must be at least 1");
        }
        double fullPrice = trail.getPricePerPerson() * groupSize;
        double rate = getDiscountRate(groupSize);
        double total = fullPrice - (fullPrice * rate);
        return Math.round(total * 100)/100.0;
    }
}
