package org.chat6;

import java.util.ArrayList;
import java.util.*;

public class AuthenticationCode {
    List<Map<String, Object>> list = new ArrayList<>();


    public boolean validateCode(String authenticationCode){
        for(Map<String, Object> element: list){
            System.out.println("코드 :"+element.get("AuthenticationCode"));
            if(element.get("AuthenticationCode").equals(authenticationCode)){
                return true;
            }
        }
        return false;
    }
    public void addCode(String authenticationCode, int itemCode, int itemNum){
        Map<String, Object> map = new HashMap<>();
        map.put("AuthenticationCode",authenticationCode);
        map.put("ItemCode",itemCode);
        map.put("ItemNum",itemNum);

        list.add(map);
    }
    public void deleteCode(String authenticationCode){
        for(Map<String, Object> element: list){
            System.out.println("AuthenticationCode :"+element.get("AuthenticationCode"));
            if(element.get("AuthenticationCode").equals(authenticationCode)){
                list.remove(element);
                break;
            }
        }
    }

    public String generateRandomString() {
        String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";

        StringBuilder result = new StringBuilder();
        Random random = new Random();

        // Add one uppercase letter
        result.append(uppercaseLetters.charAt(random.nextInt(uppercaseLetters.length())));

        // Add one lowercase letter
        result.append(lowercaseLetters.charAt(random.nextInt(lowercaseLetters.length())));

        // Add one digit
        result.append(digits.charAt(random.nextInt(digits.length())));

        // Add seven random characters (letters or digits)
        for (int i = 0; i < 7; i++) {
            int randomIndex = random.nextInt(uppercaseLetters.length() + lowercaseLetters.length() + digits.length());
            if (randomIndex < uppercaseLetters.length()) {
                result.append(uppercaseLetters.charAt(randomIndex));
            } else if (randomIndex < uppercaseLetters.length() + lowercaseLetters.length()) {
                result.append(lowercaseLetters.charAt(randomIndex - uppercaseLetters.length()));
            } else {
                result.append(digits.charAt(randomIndex - uppercaseLetters.length() - lowercaseLetters.length()));
            }
        }

        return result.toString();
    }
}

