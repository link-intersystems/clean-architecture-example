package com.link_intersystems.car.offer;

import com.link_intersystems.car.rental.RentalCar;
import com.link_intersystems.car.rental.RentalRate;
import com.link_intersystems.money.Amount;
import com.link_intersystems.time.Period;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class RentalOffer {

    private RentalCar rentalCar;
    private Period rentalPeriod;

    public RentalOffer(RentalCar rentalCar, Period rentalPeriod) {
        this.rentalCar = requireNonNull(rentalCar);
        this.rentalPeriod = requireNonNull(rentalPeriod);
    }

    public Amount getTotalRentalAmount() {
        RentalRate rentalRate = rentalCar.getRentalRate();
        Amount rentalAmountPerDay = rentalRate.getAmount();
        return rentalAmountPerDay.multiply(rentalPeriod.getDays());
    }

    public RentalCar getRentalCar() {
        return rentalCar;
    }
}
