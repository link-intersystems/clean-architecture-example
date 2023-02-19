package com.link_intersystems.car.rental;

import com.link_intersystems.EntityFixture;
import com.link_intersystems.car.CarFixture;
import com.link_intersystems.person.customer.CustomerId;
import com.link_intersystems.time.Period;

import java.time.LocalDateTime;
import java.util.List;

public class CarRentalFixture extends EntityFixture<CarRental> {


    private CarFixture carFixture;

    public CarRentalFixture(CarFixture carFixture) {
        this.carFixture = carFixture;
    }

    @Override
    protected void init(List<CarRental> entities) {
        entities.add(createCarRental1());
        entities.add(createCarRental2());
    }

    private CarRental createCarRental1() {
        Period period = rentalPeriod(15, 8, 17, 17);
        CarRental carRental = new CarRental(carFixture.getSmartFortwo().getId(), new CustomerId(1), period.getBegin());
        carRental.setReturnDateTime(period.getEnd());
        return carRental;
    }

    private CarRental createCarRental2() {
        Period period = rentalPeriod(19, 8, 20, 8);
        return new CarRental(carFixture.getSmartFortwo().getId(), new CustomerId(1), period.getBegin());
    }


    private Period rentalPeriod(int fromDayOfMonth, int fromHour, int toDayOfMonth, int toHour) {
        return new Period(dateTime(fromDayOfMonth, fromHour), dateTime(toDayOfMonth, toHour));
    }

    private LocalDateTime dateTime(int dayOfMonth, int hour) {
        return LocalDateTime.of(2023, 1, dayOfMonth, hour, 0, 0);
    }
}
