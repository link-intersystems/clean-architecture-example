package com.link_intersystems.car.rental;

import com.link_intersystems.car.CarId;
import com.link_intersystems.money.Amount;

import static java.util.Objects.requireNonNull;

/**
 * Combines a rental {@link Amount} with a {@link com.link_intersystems.car.Car}.
 */
public class RentalRate {

    private CarId carId;
    private Amount amount;

    public RentalRate(CarId carId, Amount amount) {
        this.carId = requireNonNull(carId);
        this.amount = requireNonNull(amount);
    }

    public CarId getCarId() {
        return carId;
    }

    public Amount getAmount() {
        return amount;
    }

}
