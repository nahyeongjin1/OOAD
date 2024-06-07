package org.chat6;

import java.util.HashMap;
import java.util.Map;

public class DVM {
    private String dvm_id;
    private int x;
    private int y;

    public DVM(String dvm_id, int x, int y) {
        this.dvm_id = dvm_id;
        this.x = x;
        this.y = y;
    }

    public double getDistance() {
        double distance = Math.sqrt((2-x) * (2-x) + (2-y) * (2-y));
        return distance;
    }

    public String getDvm_id() {
        return dvm_id;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
