package com.link_intersystems.carrental.customer;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerId that = (CustomerId) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
