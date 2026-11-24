package service;

import data.DataStore;
import model.Booking;
import model.BookingStatus;
import model.Tour;
import model.TourStatus;
import util.InputHelper;

import java.util.ArrayList;

public class WeatherCancellationService {

    private DataStore store;

    public WeatherCancellationService(DataStore store){
        this.store = store;
    }

    public void cancelTour(){
        Tour tour = store.getTour(InputHelper.readString("Tour ID to cancel (bad weather): ").toUpperCase());
        if(tour == null){
            System.out.println("Tour not found.");
            return;
        }
        if(tour.getStatus() == TourStatus.CANCELLED){
            System.out.println("This tour is already cancelled.");
            return;
        }

        System.out.println(tour);
        int confirm = InputHelper.readInt("Cancel this tour? 1 = Yes, 2 = No: ",1,2);
        if(confirm == 2){
            System.out.println("Nothing was changed.");
            return;
        }

        tour.cancel();
        tour.getGuide().releaseDate(tour.getDate());
        System.out.println("Tour " + tour.getId() + " cancelled. " + tour.getGuide().getName() + " is free on " + tour.getDate() + ".");

        ArrayList<Booking> bookings = store.getBookingsForTour(tour.getId());
        int moved = 0;
        int refunded = 0;

        for(Booking b : bookings){
            if(!b.isActive()){
                continue;
            }
            Tour alternative = findAlternativeTour(tour, b.getGroupSize());
            if(alternative != null){
                alternative.reservePlaces(b.getGroupSize());
                b.moveTo(alternative);
                moved++;
                System.out.println(b.getId() + " (" + b.getGroupSize() + " hikers) moved to " + alternative.getId()
                        + " - " + alternative.getTrail().getName() + " on " + alternative.getDate());
            }
            else{
                if(b.getPayment() != null){
                    System.out.println(b.getId() + ": ");
                    b.getPayment().refund();
                }
                else {
                    System.out.println(b.getId() + ": nothing to refund (not paid yet)");
                }
                b.setStatus(BookingStatus.REFUNDED);
                refunded++;
            }
        }
        store.saveAll();
        System.out.println("Done: " + moved + " booking(s) moved, " + refunded + " booking(s) refunded.");
    }
    private Tour findAlternativeTour(Tour cancelled, int groupSize){
        for(Tour t : store.getAllTours()){
            if(t.getId().equals(cancelled.getId())){
                continue;
            }
            if(t.getStatus() != TourStatus.SCHEDULED){
                continue;
            }
            if (t.getTrail().getDifficulty().compareTo(cancelled.getTrail().getDifficulty()) > 0){
                continue;
            }
            if(t.getFreePlaces() < groupSize){
                continue;
            }
            return t;
        }
        return null;
    }

}
