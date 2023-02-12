package com.link_intersystems.rental;

import com.link_intersystems.car.CarId;
import com.link_intersystems.money.Amount;
import com.link_intersystems.time.Period;

import java.time.temporal.ChronoUnit;

import static java.util.Objects.requireNonNull;

public class RentalRate {

    private CarId carId;
    private Amount amount;
    private Period validityPeriod = Period.INFINITE;
    private ChronoUnit chronoUnit = ChronoUnit.DAYS;

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

    public void setChronoUnit(ChronoUnit chronoUnit) {
        this.chronoUnit = requireNonNull(chronoUnit);
    }

    public ChronoUnit getChronoUnit() {
        return chronoUnit;
    }

    public boolean isActive(Period period) {
        return period.overlaps(validityPeriod);
    }
}
