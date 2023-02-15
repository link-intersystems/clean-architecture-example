package com.link_intersystems.car.booking;

import com.link_intersystems.car.CarId;
import com.link_intersystems.location.station.StationId;
import com.link_intersystems.person.customer.CustomerId;
import com.link_intersystems.time.Period;

import static java.util.Objects.requireNonNull;

public class CarBooking {

    private CustomerId customerId;
    private CarId carId;
    private StationId stationId;
    private Period bookingPeriod;

    public CarBooking(CustomerId customerId, CarId carId, StationId stationId, Period bookingPeriod) {
        this.customerId = requireNonNull(customerId);
        this.carId = requireNonNull(carId);
        this.stationId = requireNonNull(stationId);
        this.bookingPeriod = requireNonNull(bookingPeriod);
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public CarId getCarId() {
        return carId;
    }

    public StationId getStationId() {
        return stationId;
    }

    public Period getBookingPeriod() {
        return bookingPeriod;
    }
}
