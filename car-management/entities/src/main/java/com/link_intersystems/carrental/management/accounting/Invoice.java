package com.link_intersystems.carrental.management.accounting;

import com.link_intersystems.carrental.management.rental.CarRental;
import com.link_intersystems.carrental.money.Amount;
import com.link_intersystems.carrental.time.Period;

public class Invoice {

    private CarRental carRental;
    private RentalCar rentalCar;

    public Invoice(CarRental carRental, RentalCar rentalCar) {
        this.carRental = carRental;
        this.rentalCar = rentalCar;
    }

    public Amount getTotal() {
        Period rentalPeriod = carRental.getRentalPeriod();
        int days = rentalPeriod.getDays();
        Amount dailyRate = rentalCar.getDailyRate();
        return dailyRate.multiply(days);
    }
}
