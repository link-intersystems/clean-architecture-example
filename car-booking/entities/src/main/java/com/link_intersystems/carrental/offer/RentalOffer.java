package com.link_intersystems.carrental.offer;

import com.link_intersystems.carrental.rental.RentalCar;
import com.link_intersystems.carrental.rental.RentalRate;
import com.link_intersystems.carrental.money.Amount;
import com.link_intersystems.carrental.time.Period;

import static java.util.Objects.*;

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
