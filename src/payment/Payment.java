package payment;

import java.time.LocalDate;

public abstract class Payment {

    //field
    private double amount;
    private String paymentDate;

    public Payment(double amount){
        setAmount(amount);
    }
    public double getAmount(){
        return amount;
    }
    public String getPaymentDate(){
        return paymentDate;
    }

    public void setAmount(double amount){
        if(amount <=0){
            throw new IllegalArgumentException("Amount must be more than 0");
        }
        this.amount = amount;
    }

    protected void markPaidToday(){
        this.paymentDate = LocalDate.now().toString(); // 2026-11-21
    }

    //abs
    public abstract boolean processPayment();
    public abstract void refund();
    public abstract String getMethod();
    public abstract String getDetail();

    @Override
    public String toString(){
        return getMethod() + " Payment: Rs " + amount + " | Date: " + paymentDate;
    }
}
