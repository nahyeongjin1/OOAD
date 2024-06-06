package chat6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthenticationCode {
    List<Map<String, Object>> list = new ArrayList<>();

    AuthenticationCode(){
        Map<String, Object> map1 = new HashMap<String,Object>();
        map1.put("AuthenticationCode","abcd");
        map1.put("ItemCode",1);
        map1.put("ItemNum",2);
        list.add(map1);
        Map<String, Object> map2 = new HashMap<String,Object>();
        map2.put("AuthenticationCode","abcde");
        map2.put("ItemCode",1);
        map2.put("ItemNum",2);
        list.add(map2);
    }
    boolean validateCode(String authenticationCode){
        for(Map<String, Object> element: list){
            System.out.println("코드 :"+element.get("AuthenticationCode"));
            if(element.get("AuthenticationCode").equals(authenticationCode)){
                return true;
            }
        }
        return false;
    }
    void addCode(String authenticationCode, int itemCode, int itemNum){
        Map<String, Object> map = new HashMap<>();
        map.put("AuthenticationCode",authenticationCode);
        map.put("ItemCode",itemCode);
        map.put("ItemNum",itemNum);

        list.add(map);
    }
    void deleteCode(String authenticationCode){
        for(Map<String, Object> element: list){
            System.out.println("AuthenticationCode :"+element.get("AuthenticationCode"));
            if(element.get("AuthenticationCode").equals(authenticationCode)){
                list.remove(element);
            }
        }
    }
}