package app;

import data.DataStore;
import model.Difficulty;
import model.Guide;
import model.Hiker;
import model.Tour;
import model.Trail;

public class DataStoreTest {
    public static void main(String[] args) {
        DataStore store = new DataStore();

        // Test 1: add 3 trails with generated IDs
        System.out.println("--- Test 1: add 3 trails ---");
        store.addTrail(new Trail(store.nextTrailId(), "Le Pouce", "Moka", Difficulty.MODERATE, 1200, 4.5));
        store.addTrail(new Trail(store.nextTrailId(), "Pieter Both", "La Laura", Difficulty.HARD, 1800, 6.0));
        store.addTrail(new Trail(store.nextTrailId(), "Tamarind Falls", "Henrietta", Difficulty.EASY, 900, 3.0));
        System.out.println("Number of trails: " + store.getAllTrails().size());
        System.out.println("T001 = " + store.getTrail("T001").getName());
        System.out.println("T002 = " + store.getTrail("T002").getName());
        System.out.println("T003 = " + store.getTrail("T003").getName());

        // Test 2: find by ID, including one that does not exist
        System.out.println("\n--- Test 2: get by ID ---");
        System.out.println("Get T002: " + store.getTrail("T002"));
        System.out.println("Get T999: " + store.getTrail("T999"));

        // Test 3: delete one that exists and one that does not
        System.out.println("\n--- Test 3: delete ---");
        System.out.println("Delete T002 worked? " + store.deleteTrail("T002"));
        System.out.println("Delete T999 worked? " + store.deleteTrail("T999"));
        System.out.println("Number of trails: " + store.getAllTrails().size());

        // Test 4: add a NEW trail after the delete - no old trail may be lost
        System.out.println("\n--- Test 4: add after delete ---");
        String newId = store.nextTrailId();
        System.out.println("New ID given: " + newId);
        store.addTrail(new Trail(newId, "Black River Peak", "Black River", Difficulty.MODERATE, 1500, 7.0));
        System.out.println("Number of trails: " + store.getAllTrails().size() + "  (should be 3)");
        System.out.println("Tamarind Falls still there? " + (store.getTrail("T003") != null && store.getTrail("T003").getName().equals("Tamarind Falls")));

        // Test 5: the other maps work the same way
        System.out.println("\n--- Test 5: guides, hikers, tours ---");
        Guide ravi = new Guide(store.nextGuideId(), "Ravi Ramdin", "57123456", 5, Difficulty.HARD, 1500);
        store.addGuide(ravi);
        Hiker anna = new Hiker(store.nextHikerId(), "Anna Lee", "59876543", 25, Difficulty.MODERATE);
        store.addHiker(anna);
        Tour tour = new Tour(store.nextTourId(), store.getTrail("T001"), ravi, "2026-12-05", 8);
        store.addTour(tour);
        System.out.println("Guide: " + store.getGuide("G001").getName());
        System.out.println("Hiker: " + store.getHiker("H001").getName());
        System.out.println("Tour:  " + store.getTour("TR001"));
    }
}