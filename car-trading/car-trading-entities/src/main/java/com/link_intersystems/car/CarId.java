package com.link_intersystems.car;

import java.util.Objects;

public class CarId {

    private int value;

    public CarId(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarId carId = (CarId) o;
        return value == carId.value;
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
        return "CarId{" +
                "value=" + value +
                '}';
    }
}
