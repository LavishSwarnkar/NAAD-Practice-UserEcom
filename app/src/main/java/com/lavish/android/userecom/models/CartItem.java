package com.lavish.android.userecom.models;

public class CartItem {

    public String name;
    public int price;
    public float qty;

    public CartItem(String name, int price) {
        this.name = name;
        this.price = price;
        qty = 1;
    }

    public CartItem(String name, int price, float qty) {
        this.name = name;
        this.price = price;
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", qty=" + qty +
                '}';
    }
}
