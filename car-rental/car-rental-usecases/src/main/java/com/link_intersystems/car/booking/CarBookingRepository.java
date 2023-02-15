package com.link_intersystems.car.booking;

import com.link_intersystems.car.CarId;
import com.link_intersystems.time.Period;

import java.util.List;

public interface CarBookingRepository {
    CarBooking findBooking(CarId carId, Period bookingPeriod);
}
