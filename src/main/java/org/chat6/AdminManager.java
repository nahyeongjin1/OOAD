package org.chat6;

public class AdminManager {
    int id;
    int pw;


    StockManager stockManager;
    AdminManager(int id, int pw, StockManager stockManager) {
        this.id = id;
        this.pw = pw;
        this.stockManager = stockManager;
    }

    public void checkPW(int id, int pw) {
        if (this.id == id && this.pw == pw) {
            stockManager.rechargeStock();
        }
    }
}
