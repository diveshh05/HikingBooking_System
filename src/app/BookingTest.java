package app;

import java.util.ArrayList;
import model.Booking;
import model.BookingStatus;
import model.Difficulty;
import model.Guide;
import model.Hiker;
import model.Tour;
import model.Trail;
import payment.CardPayment;
import payment.Payment;

public class BookingTest {
    public static void main(String[] args) {

        // Objects from Steps 3-6
        Trail lePouce = new Trail("T001", "Le Pouce", "Moka", Difficulty.MODERATE, 1200, 4.5);
        Trail tamarind = new Trail("T003", "Tamarind Falls", "Henrietta", Difficulty.EASY, 900, 3.0);
        Guide ravi = new Guide("G001", "Ravi", "57123456", 5, Difficulty.HARD, 1500);
        Tour tour1 = new Tour("TR001", lePouce, ravi, "2026-12-05", 8);
        Tour tour2 = new Tour("TR002", tamarind, ravi, "2026-12-12", 8);
        Hiker anna = new Hiker("H001", "Anna Lee", "59876543", 25, Difficulty.MODERATE);
        Hiker tom = new Hiker("H002", "Tom Bernard", "57001122", 30, Difficulty.EASY);
        Hiker priya = new Hiker("H003", "Priya Doobur", "58334455", 22, Difficulty.HARD);

        // Test 1: a confirmed booking for a group of 2, paid by card
        System.out.println("--- Test 1: confirmed booking ---");
        ArrayList<Hiker> group = new ArrayList<>();
        group.add(anna);
        group.add(tom);
        Payment card = new CardPayment(2400, "4417");
        card.processPayment();
        Booking b1 = new Booking("B001", tour1, group, 2400, card, BookingStatus.CONFIRMED);
        System.out.println(b1);

        // Test 2: changing the ORIGINAL list afterwards must NOT change the booking
        System.out.println("\n--- Test 2: add Priya to the original list ---");
        group.add(priya);
        System.out.println("Original list size: " + group.size());
        System.out.println("Booking group size: " + b1.getGroupSize() + "  (should still be 2)");

        // Test 3: active or not
        System.out.println("\n--- Test 3: isActive ---");
        System.out.println("Active when CONFIRMED? " + b1.isActive());

        // Test 4: move the booking to another tour (weather cancellation, Algorithm 4.5)
        System.out.println("\n--- Test 4: moveTo ---");
        b1.moveTo(tour2);
        System.out.println(b1);
        System.out.println("Active when MOVED? " + b1.isActive());
        b1.setStatus(BookingStatus.REFUNDED);
        System.out.println("Active when REFUNDED? " + b1.isActive());

        // Test 5: invalid bookings are refused
        System.out.println("\n--- Test 5: invalid bookings ---");
        try {
            new Booking("B009", tour1, new ArrayList<>(), 1200, card, BookingStatus.CONFIRMED);
        } catch (IllegalArgumentException e) {
            System.out.println("Refused: " + e.getMessage());
        }
        try {
            ArrayList<Hiker> solo = new ArrayList<>();
            solo.add(anna);
            new Booking("B010", tour1, solo, 0, card, BookingStatus.CONFIRMED);
        } catch (IllegalArgumentException e) {
            System.out.println("Refused: " + e.getMessage());
        }

        // Test 6: a promoted hiker from the waiting list - not paid yet (Algorithm 4.4)
        System.out.println("\n--- Test 6: pending booking, no payment yet ---");
        ArrayList<Hiker> promoted = new ArrayList<>();
        promoted.add(priya);
        Booking b2 = new Booking("B002", tour1, promoted, 1200, null, BookingStatus.PENDING_PAYMENT);
        System.out.println(b2);
    }
}