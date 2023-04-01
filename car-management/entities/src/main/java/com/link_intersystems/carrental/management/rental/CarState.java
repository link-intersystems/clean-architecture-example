package com.link_intersystems.carrental.management.rental;

import java.util.Objects;

import static java.util.Objects.*;

public class CarState {

    private FuelLevel fuelLevel;
    private Odometer odometer;

    public CarState(FuelLevel fuelLevel, Odometer odometer) {
        this.fuelLevel = requireNonNull(fuelLevel, "fuel level must not be null");
        this.odometer = requireNonNull(odometer, "odometer must not be null");
    }

    public FuelLevel getFuelLevel() {
        return fuelLevel;
    }

    public Odometer getOdometer() {
        return odometer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarState carState = (CarState) o;
        return getFuelLevel() == carState.getFuelLevel() && Objects.equals(getOdometer(), carState.getOdometer());
    }

    @Override
    public int hashCode() {
        return hash(getFuelLevel(), getOdometer());
    }
}
