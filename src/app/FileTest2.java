package app;

import data.DataStore;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import model.Booking;
import model.BookingStatus;
import model.Difficulty;
import model.Guide;
import model.Hiker;
import model.Tour;
import model.Trail;
import payment.CardPayment;
import payment.CashPayment;
import payment.OnlinePayment;

public class FileTest2 {
    public static void main(String[] args) {

        // ===== Build a small company in memory =====
        DataStore store = new DataStore();
        Trail lePouce = new Trail("T001", "Le Pouce", "Moka", Difficulty.MODERATE, 1200, 4.5);
        Trail tamarind = new Trail("T002", "Tamarind Falls", "Henrietta", Difficulty.EASY, 900, 3.0);
        store.addTrail(lePouce);
        store.addTrail(tamarind);
        Guide ravi = new Guide("G001", "Ravi Ramdin", "57123456", 5, Difficulty.HARD, 1500);
        Guide sonia = new Guide("G002", "Sonia Pillay", "57987654", 2, Difficulty.MODERATE, 1200);
        store.addGuide(ravi);
        store.addGuide(sonia);
        Hiker anna = new Hiker("H001", "Anna Lee", "59876543", 25, Difficulty.MODERATE);
        Hiker tom = new Hiker("H002", "Tom Bernard", "57001122", 30, Difficulty.EASY);
        Hiker priya = new Hiker("H003", "Priya Doobur", "58334455", 22, Difficulty.HARD);
        store.addHiker(anna);
        store.addHiker(tom);
        store.addHiker(priya);

        // TR001: full, with Priya on the waiting list
        Tour tr1 = new Tour("TR001", lePouce, ravi, "2026-12-05", 5);
        ravi.bookDate("2026-12-05");
        store.addTour(tr1);
        // TR002: places left, NOBODY on the waiting list
        Tour tr2 = new Tour("TR002", tamarind, sonia, "2026-12-12", 8);
        sonia.bookDate("2026-12-12");
        store.addTour(tr2);

        ArrayList<Hiker> g1 = new ArrayList<>();
        g1.add(anna);
        g1.add(tom);
        tr1.reservePlaces(2);
        store.addBooking(new Booking("B001", tr1, g1, 2400, new CardPayment(2400, "4417"), BookingStatus.CONFIRMED));

        ArrayList<Hiker> g2 = new ArrayList<>();
        g2.add(priya);
        g2.add(tom);
        g2.add(anna);
        tr1.reservePlaces(3);
        store.addBooking(new Booking("B002", tr1, g2, 3600, new CashPayment(3600, 4000), BookingStatus.CONFIRMED));
        tr1.addToWaitingList(priya);

        ArrayList<Hiker> g3 = new ArrayList<>();
        g3.add(priya);
        tr2.reservePlaces(1);
        store.addBooking(new Booking("B003", tr2, g3, 900, new OnlinePayment(900, "MCB-88213"), BookingStatus.CONFIRMED));

        ArrayList<Hiker> g4 = new ArrayList<>();
        g4.add(anna);
        tr2.reservePlaces(1);
        store.addBooking(new Booking("B004", tr2, g4, 900, null, BookingStatus.PENDING_PAYMENT));

        // ===== Test 1: save everything =====
        System.out.println("--- Test 1: save ---");
        store.saveAll();
        System.out.println("Saved. Open tours.txt and bookings.txt in the data folder");

        // ===== Test 2: reopen = new empty store + loadAll =====
        System.out.println("\n--- Test 2: reload tours ---");
        DataStore reopened = new DataStore();
        reopened.loadAll();
        System.out.println("Tours loaded: " + reopened.getAllTours().size() + "  (should be 2)");
        System.out.println(reopened.getTour("TR001"));
        System.out.println(reopened.getTour("TR002"));

        // ===== Test 3: bookings, payments and the guide's busy date came back =====
        System.out.println("\n--- Test 3: reload bookings ---");
        System.out.println("Bookings loaded: " + reopened.getAllBookings().size() + "  (should be 4)");
        System.out.println(reopened.getBooking("B002"));
        System.out.println(reopened.getBooking("B004"));
        System.out.print("Refund B002 after reload -> ");
        reopened.getBooking("B002").getPayment().refund();
        System.out.println("Ravi free on 2026-12-05? " + reopened.getGuide("G001").isAvailable("2026-12-05") + "  (should be false)");
        System.out.println("First on TR001 waiting list: " + reopened.getTour("TR001").nextInWaitingList().getName());

        // ===== Test 4: a tour pointing to a trail that does not exist is skipped =====
        System.out.println("\n--- Test 4: tour with unknown trail T999 ---");
        try (BufferedWriter w = new BufferedWriter(new FileWriter("data/tours.txt", true))) {
            w.write("TR009;T999;G001;2026-12-20;8;8;SCHEDULED;-");
            w.newLine();
        } catch (IOException e) {
            System.out.println("Test could not write: " + e.getMessage());
        }
        DataStore third = new DataStore();
        third.loadAll();
        System.out.println("Tours loaded: " + third.getAllTours().size() + "  (should still be 2)");

        // clean up: save the good data again
        store.saveAll();
    }
}