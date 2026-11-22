package payment;

public class CashPayment extends Payment{

    private double amountTendered;

    public CashPayment(double amount, double amountTendered){
        super(amount);
        this.amountTendered = amountTendered;
    }

    public double getAmountTendered() {
        return amountTendered;
    }

    @Override
    public boolean processPayment(){
        if(amountTendered < getAmount()){
            System.out.println("Not enough cash");
            return false;
        }
        System.out.println("Change: Rs " + (amountTendered - getAmount()));
        markPaidToday();
        return true;
    }

    @Override
    public void refund(){
        System.out.println("Refund: Rs " + getAmount() + " handed back in cash");
    }

    @Override
    public String getMethod(){
        return "Cash";
    }
    @Override
    public String getDetail(){
        return String.valueOf(amountTendered);
    }
}
