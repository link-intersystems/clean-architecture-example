package com.link_intersystems.carrental.management.rental.returnCar;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.accounting.RentalCar;
import com.link_intersystems.carrental.management.rental.CarRental;

interface ReturnCarRepository {
    CarRental find(BookingNumber bookingNumber);

    void persist(CarRental carRental);

    RentalCar findRentalCar(BookingNumber bookingNumber);
}
