package com.link_intersystems.car.rental;

import com.link_intersystems.money.Amount;

import java.time.temporal.ChronoUnit;

import static java.util.Objects.requireNonNull;

/**
 * A rental {@link Amount} for a specific period as defined by a ({@link ChronoUnit}).
 */
public class RentalRate {

    private Amount amount;
    private ChronoUnit timePeriodUnit;

    public RentalRate(Amount amount) {
        this(amount, ChronoUnit.DAYS);
    }

    public RentalRate(Amount amount, ChronoUnit timePeriodUnit) {
        this.amount = requireNonNull(amount);
        this.timePeriodUnit = requireNonNull(timePeriodUnit);
    }

    public Amount getAmount() {
        return amount;
    }

    public ChronoUnit getTimePeriodUnit() {
        return timePeriodUnit;
    }
}
