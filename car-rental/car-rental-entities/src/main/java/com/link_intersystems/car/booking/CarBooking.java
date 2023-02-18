package com.link_intersystems.car.booking;

import com.link_intersystems.car.CarId;
import com.link_intersystems.person.customer.CustomerId;
import com.link_intersystems.time.Period;

import static java.util.Objects.requireNonNull;

public class CarBooking {

    private CustomerId customerId;
    private CarId carId;
    private Period bookingPeriod;

    public CarBooking(BookingNumber bookingNumber, CustomerId customerId, CarId carId, Period bookingPeriod) {
        this.customerId = requireNonNull(customerId);
        this.carId = requireNonNull(carId);
        this.bookingPeriod = requireNonNull(bookingPeriod);
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public CarId getCarId() {
        return carId;
    }

    public Period getBookingPeriod() {
        return bookingPeriod;
    }
}
