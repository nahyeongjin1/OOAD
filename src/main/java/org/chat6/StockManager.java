package org.chat6;

import java.util.*;

public class StockManager {
    private int[] stockList;
    private int[][] selectedStock;
    private int[] validStocks = {1,2,3,4,5};
    PaymentManager paymentManager;
    VMController vmController;
  
    public StockManager() {
        stockList = new int[20];
        Arrays.fill(stockList, 0);
        selectedStock = new int[10][20];
        for (int i = 0; selectedStock.length > i; i++) {
          Arrays.fill(selectedStock[i], 99);
        }
    }


    public void init(PaymentManager paymentManager, VMController vmController) {
        this.paymentManager = paymentManager;
        this.vmController = vmController;
    }

    public boolean checkStockAndPayment(int itemCode, int itemNum) {

        if (itemNum <= stockList[itemCode]) {
            reduceStock(itemCode, itemNum);
            // temp

            paymentManager.startPayment(itemCode, itemNum);
            return true;

        }
      return false;
    }

    public int checkStock(int itemCode, int itemNum) {
        return stockList[itemCode];
    }

    public void replenishmentStock(int clientNum) {
        for (int i = 0; i < stockList.length; i++) {
            stockList[i] += selectedStock[clientNum][i];
        }
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
            selectedStock[0][itemCode] += itemNum;
            stockList[itemCode] -= itemNum;
            return true;
        }
        return false;
    }


    public void reduceStock(int itemCode, int itemNum) {

        stockList[itemCode] -= itemNum;
    }

    public void printStockList() {
        System.out.println("------------------------------");
        for (int i = 0; i < stockList.length; i++) {
            System.out.println("item code[" + i + "] : " + stockList[i]);
        }
        System.out.println("------------------------------");

    }



}

