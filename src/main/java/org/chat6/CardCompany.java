package org.chat6;



import java.io.*;
import java.util.*;

public class CardCompany {
    List<Map<String,Object>> cards = new ArrayList<>();
    DisplayManager displayManager;

    public CardCompany(){

        try {
            String filePath = "src/main/java/org/chat6/Card.txt";

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
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void init(DisplayManager displayManager) {
        this.displayManager = displayManager;
    }

    public boolean requestPayment(String cardNumber, Integer totalPrice) {
        int balance;
        boolean isSuccess = false;

        for(Map<String,Object> element : cards) {
            if (element.get("CardNumber").equals(cardNumber)) {
                balance = (Integer)element.get("Balance");
                if (balance >= totalPrice) {
                    balance -= totalPrice;
                    element.put("Balance", balance);
                    try {
                        String filePath = "src/main/java/org/chat6/Card.txt";

                        FileWriter fileWriter = new FileWriter(filePath);
                        BufferedWriter bw = new BufferedWriter(fileWriter);

                        for(Map<String,Object> el : cards){
                            String text = el.get("CardNumber")+" "+el.get("Balance")+"\n";
                            bw.write(text);
                        }
                        bw.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    isSuccess = true;
                }
                break;
            }
        }
        return isSuccess;
    }


    int requestValidCard(String card){
        for(Map<String,Object> element : cards){
            if(element.get("CardNumber").equals(card)) {
                return (int)element.get("Balance");
            }
        }
        return -1;
    }
}
