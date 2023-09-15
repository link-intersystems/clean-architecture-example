package com.link_intersystems.carrental.management.rental;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.jdbc.JdbcTemplate;

import java.util.Map;

class JdbcGetPickupCarRepository implements GetPickupCarRepository {

    private CarRentalFactory carRentalFactory = new CarRentalFactory();
    private JdbcTemplate jdbcTemplate;

    public JdbcGetPickupCarRepository(JdbcTemplate managementJdbcTemplate) {
        this.jdbcTemplate = managementJdbcTemplate;
    }

    @Override
    public CarRental find(BookingNumber bookingNumber) {
        Map<String, Object> carPickupRow = jdbcTemplate.queryForMap("SELECT * FROM CAR_RENTAL where BOOKING_NUMBER =?", bookingNumber.getValue());
        return carRentalFactory.create(carPickupRow);
    }

}
