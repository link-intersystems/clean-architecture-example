package com.link_intersystems.car.booking;

import com.link_intersystems.EntityFixture;
import com.link_intersystems.car.CarFixture;
import com.link_intersystems.person.customer.CustomerId;
import com.link_intersystems.time.Period;

import java.time.LocalDateTime;
import java.util.List;

public class CarBookingFixture extends EntityFixture<CarBooking> {

    private CarFixture carFixture;

    public CarBookingFixture(CarFixture carFixture) {
        this.carFixture = carFixture;
    }

    @Override
    protected void init(List<CarBooking> entities) {
        entities.add(createBooking1());
        entities.add(createBooking2());
    }

    private CarBooking createBooking1() {
        Period period = rentalPeriod(15, 8, 17, 17);
        return new CarBooking(new CustomerId(1), carFixture.getSmartFortwo().getId(), period);
    }

    private CarBooking createBooking2() {
        Period period = rentalPeriod(19, 8, 20, 8);
        return new CarBooking(new CustomerId(1), carFixture.getSmartFortwo().getId(), period);
    }


    private Period rentalPeriod(int fromDayOfMonth, int fromHour, int toDayOfMonth, int toHour) {
        return new Period(dateTime(fromDayOfMonth, fromHour), dateTime(toDayOfMonth, toHour));
    }

    private LocalDateTime dateTime(int dayOfMonth, int hour) {
        return LocalDateTime.of(2023, 1, dayOfMonth, hour, 0, 0);
    }
}
