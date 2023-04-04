package com.link_intersystems.carrental.management.booking.list;

import com.link_intersystems.carrental.management.booking.CarBooking;
import com.link_intersystems.carrental.management.booking.CarBookingMapper;
import com.link_intersystems.jdbc.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class H2ListBookingsRepository implements ListBookingsRepository {

    private CarBookingMapper carBookingMapper = new CarBookingMapper();
    private JdbcTemplate jdbcTemplate;

    public H2ListBookingsRepository(JdbcTemplate managementJdbcTemplate) {
        this.jdbcTemplate = managementJdbcTemplate;
    }


    @Override
    public List<CarBooking> findBookings() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("""
                SELECT * FROM CAR_BOOKING 
                    WHERE
                     RENTAL_STATE IS NULL
                     """);
        return rows.stream().map(carBookingMapper::toCarBooking).collect(Collectors.toList());
    }


}
