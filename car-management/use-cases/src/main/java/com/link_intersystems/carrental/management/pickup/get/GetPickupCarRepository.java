package com.link_intersystems.carrental.management.pickup.get;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.pickup.CarPickup;

public interface GetPickupCarRepository {
    CarPickup find(BookingNumber bookingNumber);
}
