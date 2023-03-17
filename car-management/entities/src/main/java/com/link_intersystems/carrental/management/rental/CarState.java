package com.link_intersystems.carrental.management.rental;

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
}
