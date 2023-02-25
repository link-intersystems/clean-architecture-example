package com.link_intersystems.carrental.rental;

import com.link_intersystems.carrental.Car;
import com.link_intersystems.carrental.CarId;

import static java.util.Objects.*;

/**
 * A {@link RentalCar} is a {@link Car} that can be rented, because it has a {@link RentalRate}.
 */
public class RentalCar {

    private CarId carId;
    private RentalRate rentalRate;

    public RentalCar(CarId car, RentalRate rentalRate) {
        this.carId = requireNonNull(car);
        this.rentalRate = requireNonNull(rentalRate);
    }

    public RentalRate getRentalRate() {
        return rentalRate;
    }

    public CarId getCarId() {
        return carId;
    }
}
