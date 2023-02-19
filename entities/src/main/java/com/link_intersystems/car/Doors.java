package com.link_intersystems.car;

public class Doors {

    private int value;

    public Doors(int value) {
        if (value < 1) {
            throw new IllegalArgumentException("How do you get in a car that has no door");
        }

        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
