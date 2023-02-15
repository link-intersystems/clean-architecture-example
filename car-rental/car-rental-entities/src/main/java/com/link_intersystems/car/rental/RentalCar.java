package com.link_intersystems.car.rental;

import com.link_intersystems.car.Car;

import static java.util.Objects.requireNonNull;

/**
 * A {@link RentalCar} is a {@link Car} that can be rented, because it has a {@link RentalRate}.
 */
public class RentalCar {

    private Car car;
    private RentalRate rentalRate;

    public RentalCar(Car car, RentalRate rentalRate) {
        this.car = requireNonNull(car);
        this.rentalRate = requireNonNull(rentalRate);
    }

    public RentalRate getRentalRate() {
        return rentalRate;
    }

    public Car getCar() {
        return car;
    }

}
