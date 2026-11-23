package app;

import model.Difficulty;
import model.Trail;
import service.PriceCalculator;

public class PriceTest {
    public static void main(String[] args) {
        Trail lePouce = new Trail("T001", "Le Pouce", "Moka", Difficulty.MODERATE, 1200, 4.5);

        // Tests 1-6: the table from Algorithm 4.2 (Rs 1,200 per person) + the edges
        System.out.println("Hikers | Discount | Total (Rs)   | Expected");
        int[] sizes = {1, 3, 4, 7, 8, 9};
        double[] expected = {1200.0, 3600.0, 4320.0, 7560.0, 8160.0, 9180.0};
        for (int i = 0; i < sizes.length; i++) {
            int n = sizes[i];
            double rate = PriceCalculator.getDiscountRate(n);
            double total = PriceCalculator.calculateGroupPrice(lePouce, n);
            String result = (total == expected[i]) ? "PASS" : "FAIL";
            System.out.println("   " + n + "   |   " + (int) (rate * 100) + "%    | " + total + "  | " + expected[i] + "  " + result);
        }

        // Test 7: a group of 0 is refused
        System.out.println("\n--- Test 7: group of 0 ---");
        try {
            PriceCalculator.calculateGroupPrice(lePouce, 0);
        } catch (IllegalArgumentException e) {
            System.out.println("Refused: " + e.getMessage());
        }

        // Test 8: a price with cents
        System.out.println("\n--- Test 8: Rs 1,250.50 x 5 ---");
        Trail cents = new Trail("T009", "Test Trail", "Moka", Difficulty.EASY, 1250.50, 3.0);
        System.out.println("Total: Rs " + PriceCalculator.calculateGroupPrice(cents, 5) + "  (expected 5627.25)");
    }
}