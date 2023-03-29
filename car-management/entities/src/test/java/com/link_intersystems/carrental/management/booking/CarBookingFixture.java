package com.link_intersystems.carrental.management.booking;

import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.booking.BookingNumber;

class CarBookingFixture {

    public CarBooking getReneLinkBooking() {
        BookingNumber bookingNumber = new BookingNumber(42);
        VIN vin = new VIN("WMEEJ8AA3FK792135");
        Customer customer = new Customer("René", "Link");
        return new CarBooking(bookingNumber, vin, customer);
    }

}