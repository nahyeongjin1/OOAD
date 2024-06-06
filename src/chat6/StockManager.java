package chat6;

import java.util.*;

public class StockManager {
    private int[] stockList;
    private int[][] selectedStock;
    public StockManager() {
        stockList = new int[20];
        selectedStock = new int[10][20];
        for (int i = 0; selectedStock.length > i; i++) {
            Arrays.fill(selectedStock[i], 0);
        }
        stockList[0] = 99;
        stockList[1] = 99;
        stockList[2] = 99;
        stockList[3] = 99;
        stockList[4] = 99;
    }

    public boolean checkStock(int itemCode, int itemNum) {
        return itemNum == stockList[itemCode];
    }

    public void replenishmentStock(int clientNum) {
        for (int i = 0; i < stockList.length; i++) {
            stockList[i] += selectedStock[clientNum][i];
        }
    }

    public void rechargeStock() {
        Arrays.fill(stockList, 99);
    }

    public boolean selectStock(int itemCode, int itemNum) {
        if (itemNum >= 1 && stockList[itemCode] >= itemNum) {
            selectedStock[0][itemCode] += itemNum;
            stockList[itemCode] -= itemNum;
            return true;
        }
        return false;
    }

    public void printStockList() {
        for (int i = 0; i < stockList.length; i++) {
            System.out.println("item code[" + i + "] : " + stockList[i]);
        }
    }



}