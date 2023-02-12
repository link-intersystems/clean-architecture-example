package com.link_intersystems.rental;

import com.link_intersystems.car.Car;

public class RentalCar {

    private Car car;
    private RentalRate rentalRate;

    public RentalCar(Car car, RentalRate rentalRate) {
        this.car = car;
        this.rentalRate = rentalRate;
    }

    public Car getCar() {
        return car;
    }
}
