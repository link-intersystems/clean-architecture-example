package com.link_intersystems.carrental.booking;

import com.link_intersystems.EntityFixture;
import com.link_intersystems.carrental.CarFixture;
import com.link_intersystems.person.customer.CustomerId;
import com.link_intersystems.time.Period;

import java.util.List;

import static com.link_intersystems.time.PeriodBuilder.*;

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
        Period period = from("2023-01-15", "08:00:00").to("2023-01-17", "17:00:00");
        return new CarBooking(new CustomerId(1), carFixture.getSmartFortwo().getId(), period);
    }

    private CarBooking createBooking2() {
        Period period = from("2023-01-19", "08:00:00").to("2023-01-20", "08:00:00");
        return new CarBooking(new CustomerId(1), carFixture.getSmartFortwo().getId(), period);
    }

}
