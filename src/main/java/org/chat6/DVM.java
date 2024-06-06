package org.chat6;

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
        double distance = Math.sqrt((30-x) * (30-x) + (30-y) * (30-y));
        return distance;
    }
}
