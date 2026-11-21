package app;

import model.Difficulty;
import model.Guide;
import model.Hiker;
import model.Tour;
import model.Trail;

public class HikingApp {
    public static void main(String[] args) {
        System.out.println("Welcome to Mauritius Hiking Tours");

        // Objects from Steps 3 and 4
        Trail lePouce = new Trail("T001", "Le Pouce", "Moka", Difficulty.MODERATE, 1200, 4.5);
        Guide ravi = new Guide("G001", "Ravi Ramdin", "57123456", 5, Difficulty.HARD, 1500);
        Hiker anna = new Hiker("H001", "Anna Lee", "59876543", 25, Difficulty.MODERATE);
        Hiker tom = new Hiker("H002", "Tom Bernard", "57001122", 30, Difficulty.EASY);
        Hiker priya = new Hiker("H003", "Priya Doobur", "58334455", 22, Difficulty.HARD);

        // Test 1: a new tour starts with every place free and SCHEDULED
        System.out.println("\n--- Test 1: new tour ---");
        Tour tour = new Tour("TR001", lePouce, ravi, "2026-12-05", 5);
        System.out.println(tour);

        // Test 2: reserve 3 places
        System.out.println("\n--- Test 2: reserve 3 ---");
        tour.reservePlaces(3);
        System.out.println("Free places: " + tour.getFreePlaces());

        // Test 3: try to reserve more than what is left
        System.out.println("\n--- Test 3: reserve 4 (only 2 left) ---");
        try {
            tour.reservePlaces(4);
        } catch (IllegalArgumentException e) {
            System.out.println("Refused: " + e.getMessage());
        }

        // Test 4: try to release more than the maximum
        System.out.println("\n--- Test 4: release 10 (max is 5) ---");
        try {
            tour.releasePlaces(10);
        } catch (IllegalArgumentException e) {
            System.out.println("Refused: " + e.getMessage());
        }
        System.out.println("Free places still: " + tour.getFreePlaces());

        // Test 5: waiting list, first in first out
        System.out.println("\n--- Test 5: waiting list ---");
        tour.reservePlaces(2);
        System.out.println("Tour full? " + tour.isFull());
        tour.addToWaitingList(anna);
        tour.addToWaitingList(tom);
        tour.addToWaitingList(priya);
        System.out.println("Waiting: " + tour.getWaitingListSize());

        Hiker first = tour.nextInWaitingList();
        System.out.println("1st to get a place: " + first.getName() + " | still waiting: " + tour.getWaitingListSize());
        Hiker second = tour.nextInWaitingList();
        System.out.println("2nd to get a place: " + second.getName() + " | still waiting: " + tour.getWaitingListSize());

        // Test 6: cancel the tour (weather)
        System.out.println("\n--- Test 6: cancel ---");
        tour.cancel();
        System.out.println(tour);
    }
}