package com.link_intersystems.carrental.management.booking.create;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.booking.CarBooking;
import com.link_intersystems.carrental.management.booking.Customer;
import com.link_intersystems.jdbc.JdbcTemplate;

class H2CreateCarBookingRepository implements CreateCarBookingRepository {

    private JdbcTemplate jdbcTemplate;

    public H2CreateCarBookingRepository(JdbcTemplate managementJdbcTemplate) {
        this.jdbcTemplate = managementJdbcTemplate;
    }


    @Override
    public void persist(CarBooking carBooking) {
        BookingNumber bookingNumber = carBooking.getBookingNumber();
        String vinValue = carBooking.getVin().getValue();
        Customer customer = carBooking.getCustomer();

        jdbcTemplate.update("""
                INSERT INTO CAR_BOOKING (
                    BOOKING_NUMBER, 
                    VIN,
                    CUSTOMER_FIRSTNAME,
                    CUSTOMER_LASTNAME
                    ) VALUES (?, ?, ?, ?)""", bookingNumber.getValue(), vinValue, customer.getFirstname(), customer.getLastname());
    }
}
