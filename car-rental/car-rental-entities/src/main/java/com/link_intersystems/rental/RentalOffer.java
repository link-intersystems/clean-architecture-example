package com.link_intersystems.rental;

import com.link_intersystems.money.Amount;
import com.link_intersystems.rental.RentalRate;
import com.link_intersystems.time.Period;

public class RentalOffer {

    private RentalRate rentalRate;
    private Period rentalPeriod;

    RentalOffer(RentalRate rentalRate, Period rentalPeriod) {
        this.rentalRate = rentalRate;
        this.rentalPeriod = rentalPeriod;
    }

    public Amount getTotalRentalAmount() {
        return rentalRate.getAmount().multiply(rentalPeriod.getDays());
    }
}
