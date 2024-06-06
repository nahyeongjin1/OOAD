package org.chat6;

import java.util.*;

public class StockManager {
    private int[] stockList;

//    private int[] selectedStock;
    private int[] validStocks = {3,7,12,15,16};

    PaymentManager paymentManager;
    VMController vmController;
  
    public StockManager() {
        stockList = new int[20];
        Arrays.fill(stockList, 0);
//        selectedStock = new int[20];
//        System.out.println("stockList: " + stockList.length + " selectedStock: " + selectedStock.length);
//        Arrays.fill(selectedStock, 0);
    }


    public void init(PaymentManager paymentManager, VMController vmController) {
        this.paymentManager = paymentManager;
        this.vmController = vmController;
    }

    public int checkStock(int itemCode) {
        return stockList[itemCode-1];
    }

    public void replenishmentStock(int clientNum) {
        for (int i = 0; i < stockList.length; i++) {
//            stockList[i] += selectedStock[clientNum][i];
        }
    }

    public void restoreStock(int itemCode, int itemNum) {  //when user cancel the selection
        stockList[itemCode-1] += itemNum;
    }

    private boolean isValidStock(int itemCode) {
        for (int i = 0; i < validStocks.length; i++) {
            if (itemCode == validStocks[i]) {
                return true;
            }
        }
        return false;
    }

    public void rechargeStock() {
        for (int i = 0; i < stockList.length; i++) {
            if (isValidStock(i)) {
                stockList[i] = 99;
            }
        }
    }

    public boolean selectStock(int itemCode, int itemNum) {
        System.out.println(" itemCode : " + itemCode + ", itemNum : " + itemNum );
        if (itemCode <= 20 && itemCode >= 1 && itemNum >= 1 && stockList[itemCode - 1] >= itemNum) {
            System.out.println("itemCode: " + itemCode + " itemNum: " + itemNum);
//            selectedStock[itemCode - 1] += itemNum;
            stockList[itemCode - 1] -= itemNum;
            return true;
        }
        return false;
    }

    public void printStockList() {
        System.out.println("------------------------------");
        for (int i = 0; i < stockList.length; i++) {
            System.out.println("item code[" + i + "] : " + stockList[i]);
        }
        System.out.println("------------------------------");

    }



}

