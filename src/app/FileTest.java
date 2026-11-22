package app;

import data.DataStore;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import model.Difficulty;
import model.Guide;
import model.Hiker;
import model.Trail;

public class FileTest {
    public static void main(String[] args) {

        // Test 1: fill a store and SAVE it to the .txt files
        System.out.println("--- Test 1: save ---");
        DataStore store = new DataStore();
        store.addTrail(new Trail(store.nextTrailId(), "Le Pouce", "Moka", Difficulty.MODERATE, 1200, 4.5));
        store.addTrail(new Trail(store.nextTrailId(), "Pieter Both", "La Laura", Difficulty.HARD, 1800, 6.0));
        store.addTrail(new Trail(store.nextTrailId(), "Tamarind Falls", "Henrietta", Difficulty.EASY, 900, 3.0));
        store.addGuide(new Guide(store.nextGuideId(), "Ravi Ramdin", "57123456", 5, Difficulty.HARD, 1500));
        store.addGuide(new Guide(store.nextGuideId(), "Sonia Pillay", "57987654", 2, Difficulty.MODERATE, 1200));
        store.addHiker(new Hiker(store.nextHikerId(), "Anna Lee", "59876543", 25, Difficulty.MODERATE));
        store.addHiker(new Hiker(store.nextHikerId(), "Tom Bernard", "57001122", 30, Difficulty.EASY));
        store.saveAll();
        System.out.println("Saved. Open the data folder and look at trails.txt, guides.txt, hikers.txt");

        // Test 2: a BRAND NEW store (like reopening the program) LOADS the files
        System.out.println("\n--- Test 2: load into a new, empty store ---");
        DataStore reopened = new DataStore();
        System.out.println("Trails before loading: " + reopened.getAllTrails().size());
        reopened.loadAll();
        System.out.println("Trails after loading:  " + reopened.getAllTrails().size() + "  (should be 3)");
        System.out.println("Guides after loading:  " + reopened.getAllGuides().size() + "  (should be 2)");
        System.out.println("Hikers after loading:  " + reopened.getAllHikers().size() + "  (should be 2)");
        System.out.println(reopened.getTrail("T002"));
        System.out.println(reopened.getGuide("G001"));
        System.out.println(reopened.getHiker("H002"));

        // Test 3: a broken line in the file must be SKIPPED, not crash the program
        System.out.println("\n--- Test 3: broken line in trails.txt ---");
        try (BufferedWriter w = new BufferedWriter(new FileWriter("data/trails.txt", true))) {   // true = add to the end
            w.write("T009;Broken Trail;Moka;VERY_HARD;abc;2.0");
            w.newLine();
        } catch (IOException e) {
            System.out.println("Test could not write: " + e.getMessage());
        }
        DataStore third = new DataStore();
        third.loadAll();
        System.out.println("Trails loaded: " + third.getAllTrails().size() + "  (should still be 3)");

        // Clean up: save the good data again so the broken line is gone
        store.saveAll();
    }
}