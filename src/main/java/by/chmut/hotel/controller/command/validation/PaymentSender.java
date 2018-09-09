package by.chmut.hotel.controller.command.validation;

public class PaymentSender {

    private String numCard;

    private String nameCard;

    private String cvc2;


    public PaymentSender(String numCard, String nameCard, String cvc2) {
        this.numCard = numCard;
        this.nameCard = nameCard;
        this.cvc2 = cvc2;
    }

    public String payment() {
        String response = "false";
        if ((numCard!=null)&&(nameCard!=null)&&(cvc2!=null)) {
            response = "true";
        }
        return response;
    }
}
