package com.link_intersystems.carrental;

/**
 * A vehicle that is registered by the car rental service. It is available for renting if it is
 * registered as a {@link com.link_intersystems.carrental.rental.RentalCar}.
 */
public class Car {

    private CarId id;
    private String name;
    private VehicleType vehicleType;
    private Specs specs;

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

    public void setSpecs(Specs specs) {
        this.specs = specs;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", vehicleType=" + vehicleType +
                '}';
    }
}
