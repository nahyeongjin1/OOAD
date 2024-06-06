package org.example;


import java.io.*;
import java.util.*;

public class Card_Company {
    List<Map<String,Object>> cards = new ArrayList<>();
    DisplayManager displayManager;

    public void Card_Company(){

        try {
            String filePath = "src/chat6/Card.txt";
            FileReader fileReader = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fileReader);
            String line;
            int tmp;
            String[] arr = new String[2];

            while((line = br.readLine())!=null){
                arr[0] = line.substring(0,8);
                tmp=line.indexOf(" ");
                arr[1] = line.substring(tmp+1);

                Map<String,Object> map = new HashMap<>();
                map.put("CardNumber",arr[0]);
                map.put("Balance",arr[1]);

                cards.add(map);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void init(DisplayManager displayManager) {
        this.displayManager = displayManager;
    }

    public boolean requestPayment(String cardNumber, Integer totalPrice) {
        int balance;
        for(Map<String,Object> element : cards) {
            if (element.get("CardNumber").equals(cardNumber)) {
                balance = (Integer)element.get("Balance");

            }
        }
        boolean isSuccess = false;
        return isSuccess;
    }
}
