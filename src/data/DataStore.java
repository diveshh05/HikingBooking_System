package data;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class DataStore {

    private HashMap<String, Trail> trails = new HashMap<>();
    private HashMap<String, Guide> guides = new HashMap<>();
    private HashMap<String, Hiker> hikers = new HashMap<>();
    private HashMap<String, Tour> tours = new HashMap<>();
    private HashMap<String, Booking> bookings = new HashMap<>();

    public String generateId(String prefix, HashMap<String, ?> map){
        int number = 1;
        while(map.containsKey(String.format("%s%03d", prefix, number))){
            number++;
        }
        return String.format("%s%03d", prefix, number);
    }

    public String nextTrailId(){
        return generateId("T", trails);
    }
    public String nextGuideId(){
        return generateId("G", guides);
    }
    public String nextHikerId(){
        return generateId("H", hikers);
    }
    public String nextTourId(){
        return generateId("TR", tours);
    }
    public String nextBookingId(){
        return generateId("B", bookings);
    }

    // trails
    public void addTrail(Trail t){
        trails.put(t.getId(), t);
    }
    public Trail getTrail(String id){
        return trails.get(id);
    }
    public ArrayList<Trail> getAllTrails(){
        return new ArrayList<>(trails.values());
    }
    public boolean deleteTrail(String id){
        return trails.remove(id) != null;
    }

    // guides
    public void addGuide(Guide g){
        guides.put(g.getId(), g);
    }
    public Guide getGuide(String id){
        return guides.get(id);
    }
    public ArrayList<Guide> getAllGuides(){
        return new ArrayList<>(guides.values());
    }
    public boolean deleteGuide(String id){
        return guides.remove(id) != null;
    }


    // Hiker
    public void addHiker(Hiker h){
        hikers.put(h.getId(), h);
    }
    public Hiker getHiker(String id){
        return hikers.get(id);
    }
    public ArrayList<Hiker> getAllHikers(){
        return new ArrayList<>(hikers.values());
    }
    public boolean deleteHiker(String id){
        return hikers.remove(id) != null;
    }


    // Tours
    public void addTour(Tour t){
        tours.put(t.getId(), t);
    }
    public Tour getTour(String id){
        return tours.get(id);
    }
    public ArrayList<Tour> getAllTours(){
        return new ArrayList<>(tours.values());
    }
    public boolean deleteTour(String id){
        return tours.remove(id) != null;
    }


    // booking
    public void addBooking(Booking b){
        bookings.put(b.getId(), b);
    }
    public Booking getBooking(String id){
        return bookings.get(id);
    }
    public ArrayList<Booking> getAllBookings(){
        return new ArrayList<>(bookings.values());
    }
    public boolean deleteBooking(String id){
        return bookings.remove(id) != null;
    }

    public ArrayList<Booking> getBookingsForTour(String tourId){
        ArrayList<Booking> result = new ArrayList<>();
        for(Booking b : bookings.values()){
            if(b.getTour().getId().equals(tourId)){
                result.add(b);
            }
        }
        return result;
    }
}
