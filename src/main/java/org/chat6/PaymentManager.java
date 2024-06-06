package org.chat6;


public class PaymentManager {
    CardCompany cardCompany;

    public PaymentManager() {}

    public void init(CardCompany cardCompany) {
        this.cardCompany = cardCompany;
    }

    public boolean startPayment(String cardNumber, Integer totalPrice) {
        return cardCompany.requestPayment(cardNumber, totalPrice);
    }
}
