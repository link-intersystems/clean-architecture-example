package com.link_intersystems.carrental.management.pickup;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;

public class H2PickupCarRepository implements PickupCarRepository {

    private JdbcTemplate jdbcTemplate;

    public H2PickupCarRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void persist(CarPickup carPickup) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO CAR_PICKUP(" +
                    "BOOKING_NUMBER, " +
                    "DRIVER_FIRSTNAME, " +
                    "DRIVER_LASTNAME, " +
                    "DRIVER_LICENCE, " +
                    "PICKUP_TIME, " +
                    "CAR_STATE_FUEL, " +
                    "CAR_STATE_ODOMETER) VALUES (" +
                    "?, " +
                    "?, " +
                    "? , " +
                    "?, " +
                    "? , " +
                    "?, " +
                    "?)");
            ps.setObject(1, carPickup.getBookingNumber().getValue());
            ps.setObject(2, carPickup.getDriver().getFirstname());
            ps.setObject(3, carPickup.getDriver().getLastname());
            ps.setObject(4, carPickup.getDriver().getDrivingLicenceNumber());
            ps.setObject(5, carPickup.getPickupDateTime());
            ps.setObject(6, carPickup.getCarState().getFuelLevel().getPercent());
            ps.setObject(7, carPickup.getCarState().getOdometer().getValue());
            return ps;
        });
    }
}
