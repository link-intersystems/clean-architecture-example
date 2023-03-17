package com.link_intersystems.carrental.management.rental;

import static java.util.Objects.*;

public class Odometer {

    public static Odometer of(Integer value) {
        return new Odometer(requireNonNull(value, "odometer must not be null").intValue());
    }

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
