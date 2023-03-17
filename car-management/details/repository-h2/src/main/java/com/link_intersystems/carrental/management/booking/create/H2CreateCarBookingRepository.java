package com.link_intersystems.carrental.management.booking.create;

import com.link_intersystems.carrental.management.booking.CarBooking;
import org.springframework.jdbc.core.JdbcTemplate;

class H2CreateCarBookingRepository implements CreateCarBookingRepository {

    private JdbcTemplate jdbcTemplate;

    public H2CreateCarBookingRepository(JdbcTemplate managementJdbcTemplate) {
        this.jdbcTemplate = managementJdbcTemplate;
    }


    @Override
    public void persist(CarBooking carBooking) {
        jdbcTemplate.update("INSERT INTO CAR_BOOKING (BOOKING_NUMBER, VIN) VALUES (?, ?)", carBooking.getBookingNumber(), carBooking.getVin());
    }
}
