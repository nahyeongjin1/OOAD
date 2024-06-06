package org.chat6;


public class PaymentManager {
    CardCompany cardCompany;

    public PaymentManager() {}

    public void init(CardCompany cardCompany) {
        this.cardCompany = cardCompany;
    }

    public void startPayment(String cardNumber, Integer totalPrice) {
        cardCompany.requestPayment(cardNumber, totalPrice);
    }
}
