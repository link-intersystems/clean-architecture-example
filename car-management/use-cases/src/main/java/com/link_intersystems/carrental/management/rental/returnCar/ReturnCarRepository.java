package com.link_intersystems.carrental.management.rental.returnCar;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.rental.CarRental;

interface ReturnCarRepository {
    CarRental find(BookingNumber bookingNumber);

    void update(CarRental carRental);
}
