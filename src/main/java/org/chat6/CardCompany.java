package org.chat6;



import java.io.*;
import java.util.*;

public class CardCompany {
    Map<String,Integer> cards = new HashMap<>();  //CardNumber, Balance

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

                cards.put(arr[0], Integer.parseInt(arr[1]));  //CardNumber, Balance
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public boolean requestPayment(String cardNumber, Integer totalPrice) {
        int balance;
        boolean isSuccess = false;

        for(Map.Entry<String,Integer> element : cards.entrySet()) {
            if (element.getKey().equals(cardNumber)) {
                balance = element.getValue();
                if (balance >= totalPrice) {
                    balance -= totalPrice;
                    element.setValue(balance);
                    try {
                        String filePath = "src/main/java/org/chat6/Card.txt";

                        FileWriter fileWriter = new FileWriter(filePath);
                        BufferedWriter bw = new BufferedWriter(fileWriter);

                        for(Map.Entry<String,Integer> el : cards.entrySet()){
                            String text = el.getKey()+" "+el.getValue()+"\n";
                            bw.write(text);
                        }

                        bw.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    isSuccess = true;
                    return isSuccess;
                }
                break;
            }
        }
        return isSuccess;
    }


    int requestValidCard(String card){
        if(cards.containsKey(card))
            return 1;
        else
            return -1;
    }
}
