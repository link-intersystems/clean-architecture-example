package com.link_intersystems.rental;

import com.link_intersystems.car.CarId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CarRentalFixture {

    private List<CarRental> carRentals = new ArrayList<>();

    public CarRentalFixture() {
        carRentals.add(createCarRental1());
        carRentals.add(createCarRental2());
    }

    private CarRental createCarRental1() {
        RentalPeriod rentalPeriod = rentalPeriod(15, 8, 17, 17);
        return new CarRental(1, new CarId(1), 1, rentalPeriod);
    }

    private CarRental createCarRental2() {
        RentalPeriod rentalPeriod = rentalPeriod(19, 8, 20, 8);
        return new CarRental(1, new CarId(1), 1, rentalPeriod);
    }


    private RentalPeriod rentalPeriod(int fromDayOfMonth, int fromHour, int toDayOfMonth, int toHour) {
        return new RentalPeriod(dateTime(fromDayOfMonth, fromHour), dateTime(toDayOfMonth, toHour));
    }

    private LocalDateTime dateTime(int dayOfMonth, int hour) {
        return LocalDateTime.of(2023, 1, dayOfMonth, hour, 0, 0);
    }

    public CarRentals getCarRentals() {
        return new CarRentals(carRentals);
    }
}
