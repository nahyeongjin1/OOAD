package org.chat6;


public class PaymentManager {
    CardCompany cardCompany;
    private String cardNumber;


    public PaymentManager(CardCompany cardCompany) {
        this.cardCompany = cardCompany;
        cardNumber = "";
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void startPayment(int itemCode, int itemNum) {
        int totalPrice = itemCode * itemNum;
        cardCompany.requestPayment(cardNumber, totalPrice);
    }
}
