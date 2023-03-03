package com.link_intersystems.carrental;

import java.util.Objects;

/**
 * Vehicle Identification Number
 */
public class VIN {
    private String value;

    public VIN(String value) {
        if (value.length() != 17) {
            throw new IllegalArgumentException("Not a vehicle identification number");
        }

        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VIN vin = (VIN) o;
        return Objects.equals(value, vin.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
