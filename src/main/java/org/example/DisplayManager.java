package org.example;

import java.util.Map;

public class DisplayManager {
    StockManager stockManager;
    Card_Company cardCompany;
    public DisplayManager() {
        stockManager = new StockManager();
        cardCompany = new Card_Company();
    }
    public boolean checkStock(Map<Item, Integer> selectedItem) {
        return true;
    }
    public void reduceStock(Map<Item, Integer> selectedItem) {

    }
    public void displayPaymentSuccess() {

    }
    public void displayInsufficientBalance() {

    }
}
