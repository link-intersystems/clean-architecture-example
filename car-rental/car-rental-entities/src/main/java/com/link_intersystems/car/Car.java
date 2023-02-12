package com.link_intersystems.car;

public class Car {

    private CarId id;
    private String name;
    private VehicleType vehicleType;
    private Specs specs;

    public CarId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public Specs getSpecs() {
        return specs;
    }
}
