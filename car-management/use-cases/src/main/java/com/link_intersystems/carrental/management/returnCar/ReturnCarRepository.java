package com.link_intersystems.carrental.management.returnCar;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.accounting.RentalCar;
import com.link_intersystems.carrental.management.pickup.CarPickup;

public interface ReturnCarRepository {
    void persist(CarReturn carReturn);

    CarPickup findPickup(BookingNumber bookingNumber);

    RentalCar findRentalCar(BookingNumber bookingNumber);
}
