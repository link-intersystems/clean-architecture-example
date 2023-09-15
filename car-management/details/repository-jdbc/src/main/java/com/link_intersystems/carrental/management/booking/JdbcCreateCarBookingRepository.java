package com.link_intersystems.carrental.management.booking;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.jdbc.JdbcTemplate;

class JdbcCreateCarBookingRepository implements CreateCarBookingRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcCreateCarBookingRepository(JdbcTemplate managementJdbcTemplate) {
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
