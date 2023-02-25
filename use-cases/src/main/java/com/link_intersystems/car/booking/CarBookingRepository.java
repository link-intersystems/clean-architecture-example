package com.link_intersystems.car.booking;

import com.link_intersystems.car.CarId;
import com.link_intersystems.person.customer.CustomerId;
import com.link_intersystems.time.Period;

interface CarBookingRepository {
    CarBooking findBooking(CarId carId, Period bookingPeriod);

    void persist(CarBooking carBooking);

    boolean isCustomerExistent(CustomerId customerId);
}
