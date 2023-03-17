package com.link_intersystems.carrental.management.booking.list;

import com.link_intersystems.carrental.management.booking.CarBooking;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

class H2ListBookingsRepository implements ListBookingsRepository {

    private JdbcTemplate jdbcTemplate;

    public H2ListBookingsRepository(JdbcTemplate managementJdbcTemplate) {
        this.jdbcTemplate = managementJdbcTemplate;
    }


    @Override
    public List<CarBooking> findBookings(LocalDateTime from, LocalDateTime to) {
        return jdbcTemplate.query("SELECT * FROM CAR_BOOKING WHERE RENTAL_STATE IS NULL", this::mapCarBookingRow);
    }

    private CarBooking mapCarBookingRow(ResultSet rs, int rowNum) throws SQLException {
        int bookingNumber = rs.getInt("BOOKING_NUMBER");
        String vin = rs.getString("VIN");
        CarBooking carBooking = new CarBooking(bookingNumber, vin);
        return carBooking;
    }
}
