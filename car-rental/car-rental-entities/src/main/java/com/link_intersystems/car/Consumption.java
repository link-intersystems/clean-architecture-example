package com.link_intersystems.car;

public class Consumption {

    private EnergyCarrier energyCarrier;
    private double energyUnitsPerKm;

    public Consumption(EnergyCarrier energyCarrier, double energyUnitsPerKm) {
        this.energyCarrier = energyCarrier;
        this.energyUnitsPerKm = energyUnitsPerKm;
    }

    public double getEnergyUnitsPerKm() {
        return energyUnitsPerKm;
    }

    public EnergyCarrier getEnergyType() {
        return energyCarrier;
    }
}
