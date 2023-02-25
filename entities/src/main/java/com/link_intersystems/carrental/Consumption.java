package com.link_intersystems.carrental;

public class Consumption {

    private FuelType fuelType;
    private double unitsPerKm;

    public Consumption(FuelType fuelType, double unitsPerKm) {
        this.fuelType = fuelType;
        this.unitsPerKm = unitsPerKm;
    }

    public double getUnitsPerKm() {
        return unitsPerKm;
    }

    public FuelType getFuelType() {
        return fuelType;
    }
}
