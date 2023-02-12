package com.link_intersystems.car.rental;

import com.link_intersystems.car.CarId;
import com.link_intersystems.money.Amount;
import com.link_intersystems.time.Period;

import static java.util.Objects.requireNonNull;

public class RentalRate {

    private CarId carId;
    private Amount amount;
    private Period validityPeriod = Period.INFINITE;

    public RentalRate(CarId carId, Amount amount) {
        this.carId = requireNonNull(carId);
        this.amount = requireNonNull(amount);
    }

    public void setValidityPeriod(Period validityPeriod) {
        this.validityPeriod = requireNonNull(validityPeriod);
    }

    public Period getValidityPeriod() {
        return validityPeriod;
    }

    public CarId getCarId() {
        return carId;
    }

    public Amount getAmount() {
        return amount;
    }

    public boolean isActive(Period period) {
        return period.overlaps(validityPeriod);
    }
}
