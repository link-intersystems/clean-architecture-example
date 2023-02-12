package com.link_intersystems.car.rental;

import com.link_intersystems.EntityFixture;
import com.link_intersystems.car.CarId;
import com.link_intersystems.time.Period;

import java.time.LocalDateTime;
import java.util.List;

public class CarRentalFixture extends EntityFixture<CarRental> {

    @Override
    protected void init(List<CarRental> entities) {
        entities.add(createCarRental1());
        entities.add(createCarRental2());
    }

    private CarRental createCarRental1() {
        Period period = rentalPeriod(15, 8, 17, 17);
        return new CarRental(1, new CarId(1), 1, period);
    }

    private CarRental createCarRental2() {
        Period period = rentalPeriod(19, 8, 20, 8);
        return new CarRental(1, new CarId(1), 1, period);
    }


    private Period rentalPeriod(int fromDayOfMonth, int fromHour, int toDayOfMonth, int toHour) {
        return new Period(dateTime(fromDayOfMonth, fromHour), dateTime(toDayOfMonth, toHour));
    }

    private LocalDateTime dateTime(int dayOfMonth, int hour) {
        return LocalDateTime.of(2023, 1, dayOfMonth, hour, 0, 0);
    }
}
