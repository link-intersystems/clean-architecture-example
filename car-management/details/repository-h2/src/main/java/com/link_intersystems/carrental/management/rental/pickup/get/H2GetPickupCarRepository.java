package com.link_intersystems.carrental.management.rental.pickup.get;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.rental.CarRental;
import com.link_intersystems.carrental.management.rental.CarRentalMapper;
import com.link_intersystems.jdbc.JdbcTemplate;

import java.util.Map;

class H2GetPickupCarRepository implements GetPickupCarRepository {

    private CarRentalMapper carRentalMapper = new CarRentalMapper();
    private JdbcTemplate jdbcTemplate;

    public H2GetPickupCarRepository(JdbcTemplate managementJdbcTemplate) {
        this.jdbcTemplate = managementJdbcTemplate;
    }

    @Override
    public CarRental find(BookingNumber bookingNumber) {
        Map<String, Object> carPickupRow = jdbcTemplate.queryForMap("SELECT * FROM CAR_RENTAL where BOOKING_NUMBER =?", bookingNumber.getValue());
        return carRentalMapper.toCarRental(carPickupRow);
    }

}
