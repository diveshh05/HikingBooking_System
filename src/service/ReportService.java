package service;

import data.DataStore;
import model.*;
import util.InputHelper;

import java.util.HashMap;

public class ReportService {

    private DataStore store;

    public ReportService(DataStore store){
        this.store = store;
    }
    public void reportMenu(){
        while(true){
            System.out.println("\n----- Reports -----");
            System.out.println("1. Earnings per guide");
            System.out.println("2. Most popular trail");
            System.out.println("3. Tour occupancy");
            System.out.println("0. Back");
            int choice = InputHelper.readInt("Choose (0-3): ", 0,3);

            switch(choice){
                case 1:
                    earningPerGuide();
                    break;
                case 2:
                    mostPopularTrail();
                    break;
                case 3:
                    tourOccupancy();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void earningPerGuide(){
        HashMap<String, Double> totals = new HashMap<>();
        for(Guide g : store.getAllGuides()){
            totals.put(g.getName(), 0.0);
        }
        for(Booking b : store.getAllBookings()){
            if(b.getStatus() == BookingStatus.CONFIRMED || b.getStatus() == BookingStatus.MOVED){
                String guideName = b.getTour().getGuide().getName();
                totals.put(guideName, totals.get(guideName) + b.getTotalPrice());
            }
        }
        System.out.println("\n=== Earnings per guide (confirmed and moved bookings) ===");
        double grandTotal = 0;
        for(String name : totals.keySet()){        //every guide name in the HashMap
            System.out.println(name + ": Rs " +String.format("%.2f", totals.get(name)));
            grandTotal = grandTotal + totals.get(name);
        }
        System.out.println("Total for the company: Rs " + String.format("%.2f", grandTotal));
    }

    private void mostPopularTrail(){
        HashMap<String, Integer> hikersPerTrail = new HashMap<>();
        for(Booking b: store.getAllBookings()){
            if(b.isActive()){
                String trailName = b.getTour().getTrail().getName();
                if(!hikersPerTrail.containsKey(trailName)){
                    hikersPerTrail.put(trailName, 0);
                }
                hikersPerTrail.put(trailName, hikersPerTrail.get(trailName) + b.getGroupSize());
            }
        }
        if(hikersPerTrail.isEmpty()){
            System.out.println("No bookings yet.");
            return;
        }
        System.out.println("\n=== Hikers booked per trail ===");
        String bestTrail = "";
        int bestCount = 0;
        for(String trail : hikersPerTrail.keySet()){
            int count = hikersPerTrail.get(trail);
            System.out.println(trail + ": " + count + " hikers(s)");
            if(count>bestCount){
                bestCount = count;
                bestTrail = trail;
            }
        }
        System.out.println("Most popular trail: " + bestTrail + " (" + bestCount + " hikers)");
    }

    private void tourOccupancy(){
        if(store.getAllTours().isEmpty()){
            System.out.println("No tours yet.");
            return;
        }

        System.out.println("\n=== Tour occupancy ===");
        for(Tour t : store.getAllTours()){
            if(t.getStatus() == TourStatus.CANCELLED){
                System.out.println(t.getId() + " | " + t.getTrail().getName() + " | " + t.getDate() + " | CANCELLED");
                continue;
            }
            int booked = t.getMaxPlaces() - t.getFreePlaces();
            int percent = booked * 100 / t.getMaxPlaces();
            System.out.println(t.getId() + " | " + t.getTrail().getName() + " | " + t.getDate() + " | "
                    + booked + "/" + t.getMaxPlaces() + " places booked (" + percent + "%) | " + t.getWaitingListSize() + " waiting");
        }
    }

}
