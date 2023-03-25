package com.link_intersystems.carrental.booking;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingNumber that = (BookingNumber) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "BookingNumber{" +
                "value=" + value +
                '}';
    }

}
