package org.example;


public class PaymentManager {
    Card_Company cardCompany;

    public PaymentManager() {}

    public void setCardCompany(Card_Company cardCompany) {
        this.cardCompany = cardCompany;
    }

    public void startPayment(String cardNumber, Integer totalPrice) {
        cardCompany.requestPayment(cardNumber, totalPrice);
    }
}
