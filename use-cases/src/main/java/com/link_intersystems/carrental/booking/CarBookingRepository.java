package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.customer.CustomerId;
import com.link_intersystems.time.Period;

interface CarBookingRepository {
    CarBooking findBooking(CarId carId, Period bookingPeriod);

    void persist(CarBooking carBooking);

    boolean isCustomerExistent(CustomerId customerId);
}
