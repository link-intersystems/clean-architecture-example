package com.link_intersystems.carrental.management.pickup;

public class Odometer {

    private int value;

    public Odometer(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("An odometer can not have a value less then 0");
        }

        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
