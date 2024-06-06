package org.example;

import java.util.*;


public class StockManager {
    private int[] stockList;
    private int[][] selectedStock;
    PaymentManager paymentManager;

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

    public void init(PaymentManager paymentManager) {
        this.paymentManager = paymentManager;
    }

    public boolean checkStock(int itemCode, int itemNum) {

        if (itemNum <= stockList[itemCode]) {
            reduceStock(itemCode, itemNum);

            // 원래 boolean return을 하고 땡 하려고 했는데
            // 그러면 결제로 자동으로 안 가네 흐음

            return true;
        }
        return false;
    }

    // checkStock 함수 내부에서 실행할 예정
    // 유효성 검사는 checkStock에서 진행 후 실행되기에
    // 추가적인 검사는 필요하지 않고 재고 줄이기만 하면 됨
    public void reduceStock(int itemCode, int itemNum) {

        stockList[itemCode] -= itemNum;
    }

}
