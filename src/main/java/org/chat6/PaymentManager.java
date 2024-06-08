package org.chat6;


public class PaymentManager {
    private CardCompany cardCompany;
    private DisplayManager displayManager;
    private String cardNumber;


    public PaymentManager(CardCompany cardCompany) {
        this.cardCompany = cardCompany;
        cardNumber = "";
    }

    public void init(DisplayManager displayManager) {
        this.displayManager = displayManager;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public boolean startPayment(int itemCode, int itemNum) {
        int itemPrice = displayManager.calcPrice(itemCode);
        int totalPrice = itemPrice * itemNum;
        return cardCompany.requestPayment(cardNumber, totalPrice);
    }
}
