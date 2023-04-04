package com.link_intersystems.carrental.management.booking;

import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.booking.BookingNumber;

import java.util.Map;

public class CarBookingMapper {
    public CarBooking toCarBooking(Map<String, Object> row) {
        BookingNumber bookingNumber = new BookingNumber((Integer) row.get("BOOKING_NUMBER"));
        VIN vin = new VIN((String) row.get("VIN"));

        String customerFirstname = (String) row.get("CUSTOMER_FIRSTNAME");
        String customerLastname = (String) row.get("CUSTOMER_LASTNAME");
        Customer customer = new Customer(customerFirstname, customerLastname);

        return new CarBooking(bookingNumber, vin, customer);
    }
}
