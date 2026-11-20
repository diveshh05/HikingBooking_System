package app;

import model.Difficulty;
public class HikingApp {

    public static void main(String[] args){

        System.out.println(Difficulty.MODERATE);

        for(Difficulty d : Difficulty.values()){
            System.out.println(d);
        }

        System.out.println(Difficulty.MODERATE.ordinal());

        System.out.println(Difficulty.MODERATE.compareTo(Difficulty.HARD));
    }

}
