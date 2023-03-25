package com.link_intersystems.carrental.rental;

import com.link_intersystems.carrental.Car;
import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.money.Amount;

import static java.util.Objects.*;

/**
 * A {@link RentalCar} is a {@link Car} that can be rented, because it has a {@link RentalRate}.
 */
public class RentalCar {

    private CarId carId;
    private Amount rentalRate;

    public RentalCar(CarId car, Amount rentalRate) {
        this.carId = requireNonNull(car);
        this.rentalRate = requireNonNull(rentalRate);
    }

    public Amount getRentalRate() {
        return rentalRate;
    }

    public CarId getCarId() {
        return carId;
    }
}
