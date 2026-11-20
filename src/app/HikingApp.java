package app;

import model.Difficulty;
import model.Hiker;
import model.Trail;

public class HikingApp {
    public static void main(String[] args) {
        System.out.println("Welcome to Mauritius Hiking Tours");

        // Test 1: create and print two valid trails
        System.out.println("\n--- Test 1: valid trails ---");
        Trail lePouce = new Trail("T001", "Le Pouce", "Moka", Difficulty.MODERATE, 1200, 4.5);
        Trail pieterBoth = new Trail("T002", "Pieter Both", "La Laura", Difficulty.HARD, 1800, 6.0);
        System.out.println(lePouce);
        System.out.println(pieterBoth);

        // Test 2: invalid price is refused, the old price stays
        System.out.println("\n--- Test 2: price of Rs 0 ---");
        try {
            lePouce.setPricePerPerson(0);
        } catch (IllegalArgumentException e) {
            System.out.println("Refused: " + e.getMessage());
        }
        System.out.println("Price still: " + lePouce.getPricePerPerson());

        // Test 3: a 0 km trail is refused (this was allowed before the fix)
        System.out.println("\n--- Test 3: distance of 0 km ---");
        try {
            lePouce.setDistance(0);
        } catch (IllegalArgumentException e) {
            System.out.println("Refused: " + e.getMessage());
        }

        // Test 4: fitness check with real trails (Algorithm 4.1, Step 7)
        System.out.println("\n--- Test 4: can the hiker do these trails? ---");
        Hiker anna = new Hiker("H001", "Anna Lee", "59876543", 25, Difficulty.MODERATE);
        System.out.println("Anna can do Le Pouce? " + anna.canHandle(lePouce.getDifficulty()));
        System.out.println("Anna can do Pieter Both? " + anna.canHandle(pieterBoth.getDifficulty()));
    }
}