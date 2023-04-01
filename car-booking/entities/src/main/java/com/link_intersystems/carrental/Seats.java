package com.link_intersystems.carrental;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seats seats = (Seats) o;
        return value == seats.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
