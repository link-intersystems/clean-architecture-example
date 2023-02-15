package com.link_intersystems.car.booking;

import com.link_intersystems.car.CarId;
import com.link_intersystems.time.Period;

public interface CarBookingRepository {
    CarBooking findBooking(CarId carId, Period bookingPeriod);

    void persist(CarBooking carBooking);
}
