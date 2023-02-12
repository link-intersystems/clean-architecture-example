package com.link_intersystems.car.rental;

import com.link_intersystems.car.Car;

public class RentalCar {

    private Car car;
    private RentalRate rentalRate;

    public RentalCar(Car car, RentalRate rentalRate) {
        this.car = car;
        this.rentalRate = rentalRate;
    }

    public RentalRate getRentalRate() {
        return rentalRate;
    }

    public Car getCar() {
        return car;
    }

}
