package com.lavish.android.userecom.models;

public class Variant {

    public String name;
    public int price;

    public Variant() {
    }

    public Variant(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String nameAndPriceString() {
        return name + " - Rs. " + price;
    }

    @Override
    public String toString() {
        return "Rs. " + price;
    }
}
