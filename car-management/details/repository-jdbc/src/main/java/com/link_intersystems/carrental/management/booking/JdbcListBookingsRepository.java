package com.link_intersystems.carrental.management.booking;

import com.link_intersystems.jdbc.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class JdbcListBookingsRepository implements ListBookingsRepository {

    private CarBookingFactory carBookingFactory = new CarBookingFactory();
    private JdbcTemplate jdbcTemplate;

    public JdbcListBookingsRepository(JdbcTemplate managementJdbcTemplate) {
        this.jdbcTemplate = managementJdbcTemplate;
    }


    @Override
    public List<CarBooking> findBookings() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("""
                SELECT * FROM CAR_BOOKING 
                    WHERE
                     RENTAL_STATE IS NULL
                     """);
        return rows.stream().map(carBookingFactory::create).collect(Collectors.toList());
    }


}
