package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.*;
import jakarta.persistence.*;

@Entity(name = "Car")
@Table(name = "CAR")
public class JpaCar {

    @Id
    @Column(name = "VIN")
    private String vinValue;

    @Column(name = "NAME")
    private String name;

    @Column(name = "VEHICLE_TYPE")
    private String vehicleTypeValue;

    @Column(name = "SEATS")
    private int seatCount;

    @Column(name = "DOORS")
    private int doorCount;

    @Column(name = "FUEL_TYPE")
    private String fuelTypeValue;

    @Column(name = "CONSUMPTIONUNITS")
    private double consumptionUnitsValue;

    @Transient
    private Car car;

    public Car getDomainObject() {
        if (car == null) {
            VehicleType vehicleType = VehicleType.valueOf(vehicleTypeValue);
            car = new Car(new CarId(new VIN(vinValue)), name, vehicleType);
            FuelType fuelType = FuelType.valueOf(fuelTypeValue);
            Consumption consumption = new Consumption(fuelType, consumptionUnitsValue);
            Specs specs = new Specs(new Seats(seatCount), new Doors(doorCount), consumption);
            car.setSpecs(specs);
        }

        return car;
    }
}
