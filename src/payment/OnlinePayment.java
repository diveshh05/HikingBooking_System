package payment;

public class OnlinePayment extends Payment{

    private String transRef;

    public OnlinePayment(double amount, String transRef){
        super(amount);
        this.transRef = transRef;
    }

    public String getTransRef(){
        return transRef;
    }

    @Override
    public boolean processPayment(){
        if(transRef == null || transRef.trim().isEmpty() || transRef.contains(";")){
            System.out.println("Invalid Reference");
            return false;
        }
        markPaidToday();
        return true;
    }
    @Override
    public void refund(){
        System.out.println("Refund: Rs " + String.format("%.2f", getAmount()) + " sent back using reference " + transRef);
    }
    @Override
    public String getMethod(){
        return "Online";
    }
    @Override
    public String getDetail(){
        return transRef;
    }
}
