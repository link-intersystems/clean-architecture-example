package com.link_intersystems.carrental;

import java.util.Objects;

import static java.util.Objects.*;

public class CarId {

    private VIN vin;

    public CarId(VIN vin) {
        this.vin = requireNonNull(vin);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarId carId = (CarId) o;
        return Objects.equals(vin, carId.vin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vin);
    }

    public String getValue() {
        return vin.getValue();
    }

    @Override
    public String toString() {
        return "CarId{" +
                "vin=" + vin +
                '}';
    }
}
