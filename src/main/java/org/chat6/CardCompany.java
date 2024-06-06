package org.chat6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardCompany {
    List<Map<String,Object>> cards = new ArrayList<>();
    CardCompany(){

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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

