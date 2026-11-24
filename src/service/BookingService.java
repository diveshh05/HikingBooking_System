package service;

import data.DataStore;
import model.*;
import payment.CardPayment;
import payment.CashPayment;
import payment.OnlinePayment;
import payment.Payment;
import util.InputHelper;
import java.util.ArrayList;


public class BookingService {

    private DataStore store;

    public BookingService(DataStore store){
        this.store = store;
    }

    public void bookGroup(){
        //1
        Tour tour = store.getTour(InputHelper.readString("Tour ID: ").toUpperCase());
        if(tour == null){
            System.out.println("Tour not found");
            return;
        }
        if(tour.getStatus() == TourStatus.CANCELLED){
            System.out.println("This tour has been cancelled.");
            return;
        }
        System.out.println(tour);
        //2
        int size = InputHelper.readInt("Number of hikers in the group (1-30): ", 1,30);
        ArrayList<Hiker> group = new ArrayList<>();
        for(int i =1; i <=size; i++){
            Hiker h = store.getHiker(InputHelper.readString("Hiker " + i + " ID: ").toUpperCase());
            if(h == null){
                System.out.println("Hiker not found. Booking stopped.");
                return;
            }
            if(group.contains(h)){
                System.out.println(h.getName() + " is already in this group. Booking stopped.");
                return;
            }
            if(!h.canHandle(tour.getTrail().getDifficulty())){
                System.out.println(h.getName() + " is not fit enough for a " + tour.getTrail().getDifficulty() + " trail. Booking stopped.");
                return;
            }
            group.add(h);
        }
        //3
        if(tour.getFreePlaces() < size){
            for(Hiker h : group){
                tour.addToWaitingList(h);
            }
            store.saveAll();
            System.out.println("Only " + tour.getFreePlaces() + " place(s) left. The group was added to the waiting list (" + tour.getWaitingListSize() + " waiting).");
            return;
        }
        //4
        double total = PriceCalculator.calculateGroupPrice(tour.getTrail(), size);
        int percent = (int)(PriceCalculator.getDiscountRate(size) * 100);
        System.out.println("Price: Rs " + tour.getTrail().getPricePerPerson() + " x " + size + " hikers, " + percent + "% group discount = Rs " + total);


        Payment payment = choosePayment(total);
        if(!payment.processPayment()){
            System.out.println("Payment failed. No booking was made.");
            return;
        }
        tour.reservePlaces(size);

        Booking booking = new Booking(store.nextBookingId(), tour, group, total, payment, BookingStatus.CONFIRMED);
        store.addBooking(booking);
        store.saveAll();

        printReceipt(booking);
    }

    private Payment choosePayment(double total){
        System.out.println("1 = Cash, 2 = Card, 3 = Online");
        int method = InputHelper.readInt("Payment method", 1, 3);

        if(method == 1){
            double tendered = InputHelper.readPositiveDouble("Amount given (Rs): ");
            return new CashPayment(total, tendered);
        }
        else if(method == 2){
            String lastFour = InputHelper.readString("Last 4 digits of the card: ");
            return new CardPayment(total, lastFour);
        }
        else{
            String ref = InputHelper.readString("Transaction reference: ");
            return new OnlinePayment(total, ref);
        }
    }

    private void printReceipt(Booking b){
        Tour t = b.getTour();
        System.out.println("\n========= RECEIPT =========");
        System.out.println("Booking: " + b.getId());
        System.out.println("Tour: " + t.getId());
        System.out.println("Date:    " + t.getDate());
        System.out.println("Guide:   " + t.getGuide().getName());
        System.out.println("Hikers:  " + b.getHikerNames());
        System.out.println("Total:   Rs " + b.getTotalPrice());
        System.out.println("Paid by: " + b.getPayment().getMethod());
        System.out.println("=============================");
    }

}
