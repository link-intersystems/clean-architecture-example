package com.link_intersystems.carmanagement.booking.create;

import com.link_intersystems.carmanagement.booking.CarBooking;
import org.springframework.jdbc.core.JdbcTemplate;

public class H2CreateCarBookingRepository implements CreateCarBookingRepository {

    private JdbcTemplate jdbcTemplate;

    public H2CreateCarBookingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void persist(CarBooking carBooking) {
        jdbcTemplate.update("INSERT INTO CAR_BOOKING (BOOKING_NUMBER, VIN) VALUES (?, ?)", carBooking.getBookingNumber(), carBooking.getVin());
    }
}
