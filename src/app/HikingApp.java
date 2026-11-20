package app;

import model.Difficulty;
import model.Guide;
import model.Hiker;

public class HikingApp {
    public static void main(String[] args) {
        System.out.println("Welcome to Mauritius Hiking Tours");

        // Test 1: valid objects. Guide role should now say "Guide" (Bug 1 fixed)
        System.out.println("\n--- Test 1: valid Guide and Hiker ---");
        Guide guide = new Guide("G001", "Ravi Ramdin", "57123456", 5, Difficulty.HARD, 1500);
        Hiker hiker = new Hiker("H001", "Anna Lee", "59876543", 25, Difficulty.MODERATE);
        System.out.println(guide);
        System.out.println(hiker);

        // Test 2: bad phone. Should be REFUSED, and no Hiker gets created (Bug 2 fixed)
        System.out.println("\n--- Test 2: bad phone ---");
        try {
            Hiker badPhone = new Hiker("H002", "Tom", "abc", 30, Difficulty.EASY);
            System.out.println("Created: " + badPhone);   // should NOT print
        } catch (IllegalArgumentException e) {
            System.out.println("Refused: " + e.getMessage());
        }

        // Test 3: null name. Before the fix this CRASHED the program (Bug 3 fixed)
        System.out.println("\n--- Test 3: null name ---");
        try {
            Hiker noName = new Hiker("H003", null, "57111111", 30, Difficulty.EASY);
            System.out.println("Created: " + noName);      // should NOT print
        } catch (IllegalArgumentException e) {
            System.out.println("Refused: " + e.getMessage());
        }

        // Test 4: fitness check (enum compare)
        System.out.println("\n--- Test 4: canHandle ---");
        System.out.println("MODERATE hiker on EASY trail: " + hiker.canHandle(Difficulty.EASY));   // true
        System.out.println("MODERATE hiker on HARD trail: " + hiker.canHandle(Difficulty.HARD));   // false

        // Test 5: guide busy dates
        System.out.println("\n--- Test 5: guide dates ---");
        guide.bookDate("2026-12-05");
        System.out.println("Free on 2026-12-05 after booking? " + guide.isAvailable("2026-12-05"));  // false
        guide.releaseDate("2026-12-05");
        System.out.println("Free on 2026-12-05 after release? " + guide.isAvailable("2026-12-05"));  // true

        System.out.println("\nAll Step 3 tests finished without crashing.");
    }
}