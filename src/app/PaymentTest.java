package app;

import java.util.ArrayList;
import payment.CardPayment;
import payment.CashPayment;
import payment.OnlinePayment;
import payment.Payment;

public class PaymentTest {
    public static void main(String[] args) {

        // Test 1: cash, enough money -> change given, success
        System.out.println("--- Test 1: cash Rs 1500 for Rs 1200 ---");
        Payment cash = new CashPayment(1200, 1500);
        System.out.println("Success? " + cash.processPayment());

        // Test 2: cash, not enough money -> must FAIL
        System.out.println("\n--- Test 2: cash Rs 1000 for Rs 1200 ---");
        Payment shortCash = new CashPayment(1200, 1000);
        System.out.println("Success? " + shortCash.processPayment());

        // Test 3: card, valid and invalid last 4 digits
        System.out.println("\n--- Test 3: card ---");
        Payment card = new CardPayment(2400, "4417");
        System.out.println("Card 4417 success? " + card.processPayment());
        Payment badCard = new CardPayment(2400, "44a7");
        System.out.println("Card 44a7 success? " + badCard.processPayment());

        // Test 4: online, with and without a reference
        System.out.println("\n--- Test 4: online ---");
        Payment online = new OnlinePayment(3600, "MCB-88213");
        System.out.println("Ref MCB-88213 success? " + online.processPayment());
        Payment noRef = new OnlinePayment(3600, "");
        System.out.println("Empty ref success? " + noRef.processPayment());

        // Test 5: POLYMORPHISM - one list, one loop, three different refund() versions
        System.out.println("\n--- Test 5: polymorphism ---");
        ArrayList<Payment> payments = new ArrayList<>();
        payments.add(cash);
        payments.add(card);
        payments.add(online);
        for (Payment p : payments) {
            System.out.println(p);
            p.refund();
        }

        // Test 6: amount of Rs 0 is refused
        System.out.println("\n--- Test 6: amount Rs 0 ---");
        try {
            Payment zero = new CashPayment(0, 100);
        } catch (IllegalArgumentException e) {
            System.out.println("Refused: " + e.getMessage());
        }
    }
}