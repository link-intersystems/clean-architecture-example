package com.link_intersystems.carrental.management;

import com.link_intersystems.carmanagement.booking.CarBooking;
import com.link_intersystems.carmanagement.booking.ListBookingsRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;

public class H2ListBookingsRepository implements ListBookingsRepository {

    private JdbcTemplate jdbcTemplate;

    public H2ListBookingsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<CarBooking> findBookings(LocalDateTime from, LocalDateTime to) {
        return null;
    }
}
