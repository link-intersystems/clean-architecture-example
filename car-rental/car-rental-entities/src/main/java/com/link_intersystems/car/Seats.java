package com.link_intersystems.car;

public class Seats {

    private int value;

    public Seats(int value) {
        if (value < 1) {
            throw new IllegalArgumentException("A car should have at least one seat for the driver");
        }
        if (value > 8) {
            throw new IllegalArgumentException("We don't rent buses.");
        }

        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
