package com.link_intersystems.carrental.booking;

public class BookingNumber {

    private int value;

    public BookingNumber(int value) {
        if (value < 1) {
            throw new IllegalArgumentException("value must be 1 or greater.");
        }

        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "BookingNumber{" +
                "value=" + value +
                '}';
    }
}