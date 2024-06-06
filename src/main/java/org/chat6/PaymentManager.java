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

    public boolean startPayment(int itemPrice, int itemNum) {
        int totalPrice = itemPrice * itemNum;
        boolean paymentResult = cardCompany.requestPayment(cardNumber, totalPrice);
        return paymentResult;
    }
}
