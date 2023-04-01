package com.link_intersystems.carrental;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consumption that = (Consumption) o;
        return Double.compare(that.getUnitsPerKm(), getUnitsPerKm()) == 0 && getFuelType() == that.getFuelType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFuelType(), getUnitsPerKm());
    }

    @Override
    public String toString() {
        return "Consumption{" +
                "fuelType=" + fuelType +
                ", unitsPerKm=" + unitsPerKm +
                '}';
    }
}
