package com.link_intersystems.carrental.management.rental;

import static java.util.Objects.*;

public class Odometer implements Comparable<Odometer> {

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

    @Override
    public int compareTo(Odometer o) {
        return Integer.valueOf(value).compareTo(o.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Odometer odometer = (Odometer) o;
        return value == odometer.value;
    }

    @Override
    public int hashCode() {
        return hash(value);
    }

}
