package payment;

public class CardPayment extends Payment{

    private String cardLastFour;

    public CardPayment(double amount, String cardLastFour){
        super(amount);
        this.cardLastFour = cardLastFour;
    }

    public String getCardLastFour(){
        return cardLastFour;
    }

    @Override
    public boolean processPayment(){
        if(cardLastFour == null || cardLastFour.length() !=4){
            System.out.println("Invalid card");
            return false;
        }
        for(int i=0; i < cardLastFour.length(); i++){
            if(!Character.isDigit(cardLastFour.charAt(i))){
                System.out.println("Invalid card");
                return false;
            }
        }
        markPaidToday();
        return true;
    }
    @Override
    public void refund(){
        System.out.println("Refund: Rs " + String.format("%.2f", getAmount()) + " sent to card ending " + cardLastFour);
    }
    @Override
    public String getMethod(){
        return "Card";
    }
    @Override
    public String getDetail(){
        return cardLastFour;
    }
}
