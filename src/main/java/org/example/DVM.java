package org.example;

import java.util.HashMap;
import java.util.Map;

public class DVM {
    private String dvm_id;

    private int x;
    private int y;

    private Map<Integer, Integer> item_list = new HashMap<>();

    public DVM(String dvm_id, int x, int y) {
        this.dvm_id = dvm_id;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setItem_list(Map<Integer, Integer> item_list) {
        this.item_list = item_list;
    }

    public Map<Integer, Integer> getItem_list() {
        return item_list;
    }
}
