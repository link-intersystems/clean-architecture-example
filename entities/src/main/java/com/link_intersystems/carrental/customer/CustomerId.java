package com.link_intersystems.carrental.customer;

public class CustomerId {

    private int value;

    public CustomerId(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("value must be 0 or greater.");
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
