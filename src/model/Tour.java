package model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;

public class Tour {

    private String id;
    private Trail trail;
    private Guide guide;
    private String date;
    private int maxPlaces;
    private int freePlaces;
    private TourStatus status;


    private Queue<Hiker> waitingList = new LinkedList<>();

    public Tour(String id, Trail trail, Guide guide, String date, int maxPlaces){
        this.id = id;
        setTrail(trail);
        setGuide(guide);
        setDate(date);
        setMaxPlaces(maxPlaces);
        this.freePlaces = maxPlaces;
        this.status = TourStatus.SCHEDULED;
    }

    //getters
    public String getId(){
        return id;
    }
    public Trail getTrail(){
        return trail;
    }
    public Guide getGuide(){
        return guide;
    }
    public String getDate(){
        return date;
    }
    public int getMaxPlaces(){
        return maxPlaces;
    }
    public int getFreePlaces(){
        return freePlaces;
    }
    public TourStatus getStatus(){
        return status;
    }

    public void setTrail(Trail trail){
        if(trail == null){
            throw new IllegalArgumentException("Trail is required");
        }
        this.trail = trail;
    }

    public void setGuide(Guide guide){
        if(guide == null){
            throw new IllegalArgumentException("Guide is required");
        }
        this.guide = guide;
    }

    public void setDate(String date){
        if(date == null || date.trim().isEmpty()){
            throw new IllegalArgumentException("Date cannot be empty");
        }
        this.date = date.trim();
    }

    public void setMaxPlaces(int maxPlaces){
        if(maxPlaces <= 0){
            throw new IllegalArgumentException("Max places must be more than 6");
        }
        this.maxPlaces = maxPlaces;
    }

    public void setFreePlaces(int freePlaces){
        if(freePlaces <0 || freePlaces > maxPlaces){
            throw new IllegalArgumentException("Free places must be between 0 and " + maxPlaces);
        }
        this.freePlaces = freePlaces;
    }

    // take the places away when people book
    public void reservePlaces(int place){
        if(place <= 0){
            throw new IllegalArgumentException("Number of places must be more than 0");
        }
        if(place > freePlaces){
            throw new IllegalArgumentException("Only " + freePlaces + " places left");
        }
        freePlaces = freePlaces - place;
    }

    // give places back when people cancel
    public void releasePlaces(int place){
        setFreePlaces(freePlaces + place);
    }

    public boolean isFull(){
        return freePlaces == 0;
    }

    // waiting list
    public void addToWaitingList(Hiker h){
        waitingList.add(h);
    }
    public boolean hasWaiting(){
        return !waitingList.isEmpty();
    }
    public Hiker nextInWaitingList(){
        return waitingList.poll(); //remove first hiker out
    }
    public int getWaitingListSize(){
        return waitingList.size();
    }
    public ArrayList<Hiker> getWaitingHikers(){
        return new ArrayList<>(waitingList);
    }

    public void cancel(){
        status = TourStatus.CANCELLED;
        waitingList.clear();
    }

    @Override
    public String toString(){
        return "ID: " + id + " | Trail: " + trail.getName() + " | Guide: " + guide.getName() + " | Date: " + date + " | Free: " + freePlaces + "/" + maxPlaces
                + " | Status: " + status + " | Waiting: " + waitingList.size();
    }

}
