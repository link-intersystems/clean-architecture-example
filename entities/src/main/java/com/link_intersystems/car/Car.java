package com.link_intersystems.car;

/**
 * A vehicle that is registered by the car rental service. It is available for renting if it is
 * registered as a {@link com.link_intersystems.car.rental.RentalCar}.
 */
public class Car {

    private CarId id;
    private String name;
    private VehicleType vehicleType;
    private Specs specs;

    public Car() {
    }

    public Car(CarId id, String name, VehicleType vehicleType) {
        this.id = id;
        this.name = name;
        this.vehicleType = vehicleType;
    }

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
