package com.link_intersystems.carrental;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doors doors = (Doors) o;
        return value == doors.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
