package com.link_intersystems.carrental.management.booking.create;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.booking.CarBooking;
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
        jdbcTemplate.update("INSERT INTO CAR_BOOKING (BOOKING_NUMBER, VIN) VALUES (?, ?)", bookingNumber.getValue(), vinValue);
    }
}
