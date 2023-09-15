package com.link_intersystems.carrental.management.booking;

import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.booking.BookingNumber;

import java.util.Map;

public class CarBookingFactory {
    public CarBooking create(Map<String, Object> carBookingRow) {
        BookingNumber bookingNumber = new BookingNumber((Integer) carBookingRow.get("BOOKING_NUMBER"));
        VIN vin = new VIN((String) carBookingRow.get("VIN"));

        String customerFirstname = (String) carBookingRow.get("CUSTOMER_FIRSTNAME");
        String customerLastname = (String) carBookingRow.get("CUSTOMER_LASTNAME");
        Customer customer = new Customer(customerFirstname, customerLastname);

        return new CarBooking(bookingNumber, vin, customer);
    }
}
