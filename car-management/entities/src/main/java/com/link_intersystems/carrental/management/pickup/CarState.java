package com.link_intersystems.carrental.management.pickup;

import static java.util.Objects.*;

public class CarState {

    private FuelLevel fuelLevel;
    private Odometer odometer;

    public CarState(FuelLevel fuelLevel, Odometer odometer) {
        this.fuelLevel = requireNonNull(fuelLevel);
        this.odometer = requireNonNull(odometer);
    }

    public FuelLevel getFuelLevel() {
        return fuelLevel;
    }

    public Odometer getOdometer() {
        return odometer;
    }
}
