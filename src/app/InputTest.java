package app;

import model.Difficulty;
import util.InputHelper;

public class InputTest {
    public static void main(String[] args) {
        System.out.println("=== InputHelper test: type the values shown in the guide ===");

        // Test 1: whole number in a range (like a menu choice)
        int choice = InputHelper.readInt("Menu choice (1-5): ", 1, 5);
        System.out.println("-> You chose: " + choice);

        // Test 2: text that cannot be empty
        String name = InputHelper.readString("Trail name: ");
        System.out.println("-> Name: " + name);

        // Test 3: decimal number more than 0
        double price = InputHelper.readPositiveDouble("Price per person (Rs): ");
        System.out.println("-> Price: Rs " + price);

        // Test 4: difficulty chosen from a numbered list
        Difficulty d = InputHelper.readDifficulty("Difficulty: ");
        System.out.println("-> Difficulty: " + d);

        System.out.println("\nAll inputs read without crashing.");
    }
}