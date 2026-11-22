package model;

import payment.Payment;

import java.util.ArrayList;

public class Booking {
    //field
    private String id;
    private Tour tour;
    private ArrayList<Hiker> hikers;
    private double totalPrice;
    private Payment payment;
    private BookingStatus status;

    //constructors
    public Booking(String id, Tour tour, ArrayList<Hiker> hikers, double totalPrice, Payment payment, BookingStatus status){
        this.id = id;
        setTour(tour);
        setHikers(hikers);
        setTotalPrice(totalPrice);
        this.payment = payment;
        setStatus(status);
    }

    //encapsulation
    public String getId(){
        return id;
    }
    public Tour getTour(){
        return tour;
    }
    public ArrayList<Hiker> getHikers(){
        return hikers;
    }
    public double getTotalPrice(){
        return totalPrice;
    }
    public Payment getPayment(){
        return payment;
    }
    public BookingStatus getStatus(){
        return status;
    }

    public int getGroupSize(){
        return hikers.size();
    }

    public void setTour(Tour tour){
        if(tour == null){
            throw new IllegalArgumentException("Tour is required");
        }
        this.tour = tour;
    }

    public void setHikers(ArrayList<Hiker> hikers){
        if(hikers == null || hikers.isEmpty()){
            throw new IllegalArgumentException("A booking needs at least 1 hike");
        }
        this.hikers = new ArrayList<>(hikers); // error fix
    }

    public void setTotalPrice(double totalPrice){
        if(totalPrice <=0){
            throw new IllegalArgumentException("Total price must be more than 0");
        }
        this.totalPrice = totalPrice;
    }

    public void setPayment(Payment payment){
        this.payment = payment;
    }

    public void setStatus(BookingStatus status){
        if(status == null){
            throw new IllegalArgumentException("Booking status is required");
        }
        this.status = status;
    }

    public boolean isActive(){
        return status != BookingStatus.CANCELLED && status != BookingStatus.REFUNDED;
    }
    //weather cancel
    public void moveTo(Tour newTour){
        setTour(newTour);
        status = BookingStatus.MOVED;
    }

    public String getHikerNames(){
        String name = "";
        for(Hiker h : hikers){
            if(!name.isEmpty()){
                name = name + ", ";
            }
            name = name + h.getName();
        }
        return name;
    }

    @Override
    public String toString(){
        String payer = (payment == null) ? "none" : payment.getMethod();

        return "ID: " + id + " | Tour: " + tour.getId() + " (" + tour.getTrail().getName() + ", " + tour.getDate() + ")"
                + " | Hikers: " + getHikerNames() + " | Group: " + getGroupSize()
                + " | Total: Rs " + totalPrice + " | Payment: " + payer + " | Status: " + status;
    }

}
