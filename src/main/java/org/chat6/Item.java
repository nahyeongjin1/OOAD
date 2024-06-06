package org.chat6;

public class Item {
    public int price;
    public int code;
    public String name;

    Item(int price, int code, String name){
        this.code = code;
        this.price = price;
        this.name = name;
    }
}