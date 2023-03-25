package com.link_intersystems.carrental.management.rental.pickup;

import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.booking.CarBooking;
import com.link_intersystems.carrental.management.booking.RentalState;
import com.link_intersystems.carrental.management.rental.CarRental;
import com.link_intersystems.jdbc.JdbcTemplate;

import java.sql.*;

class H2PickupCarRepository implements PickupCarRepository {

    private JdbcTemplate jdbcTemplate;

    public H2PickupCarRepository(JdbcTemplate managementJdbcTemplate) {
        this.jdbcTemplate = managementJdbcTemplate;
    }

    @Override
    public void persist(CarRental carRental) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("""
                MERGE INTO CAR_RENTAL(
                    BOOKING_NUMBER, 
                    DRIVER_FIRSTNAME, 
                    DRIVER_LASTNAME, 
                    DRIVER_LICENCE, 
                    PICKUP_TIME, 
                    PICKUP_CAR_STATE_FUEL,
                    PICKUP_CAR_STATE_ODOMETER
                ) KEY (BOOKING_NUMBER) 
                VALUES (?, ?, ? , ?, ? , ?, ?)""");
            ps.setObject(1, carRental.getBookingNumber().getValue());
            ps.setObject(2, carRental.getDriver().getFirstname());
            ps.setObject(3, carRental.getDriver().getLastname());
            ps.setObject(4, carRental.getDriver().getDrivingLicenceNumber());
            ps.setObject(5, Timestamp.valueOf(carRental.getPickupDateTime()));
            ps.setObject(6, carRental.getPickupCarState().getFuelLevel().getPercent());
            ps.setObject(7, carRental.getPickupCarState().getOdometer().getValue());
            return ps.executeUpdate();
        });
    }

    @Override
    public CarBooking findBooking(BookingNumber bookingNumber) {
        Object[] queryArgs = {bookingNumber.getValue()};

        return jdbcTemplate.queryForObject("""
                SELECT * 
                FROM CAR_BOOKING 
                WHERE BOOKING_NUMBER = ?""", queryArgs, this::mapCarBookingRow);
    }

    @Override
    public void persist(CarBooking carBooking) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("""
                MERGE INTO CAR_BOOKING (
                    VIN, 
                    BOOKING_NUMBER, 
                    RENTAL_STATE
                    ) 
                    KEY(BOOKING_NUMBER) 
                    VALUES (?, ? ,?)""");
            ps.setObject(1, carBooking.getVin().getValue());
            ps.setObject(2, carBooking.getBookingNumber().getValue());

            RentalState rentalState = carBooking.getRentalState();
            if (rentalState == null) {
                ps.setNull(3, Types.VARCHAR);
            } else {
                ps.setObject(3, rentalState.name());
            }

            return ps.executeUpdate();
        });
    }

    private CarBooking mapCarBookingRow(ResultSet rs) throws SQLException {
        BookingNumber bookingNumber = new BookingNumber(rs.getInt("BOOKING_NUMBER"));
        VIN vin = new VIN(rs.getString("VIN"));
        CarBooking carBooking = new CarBooking(bookingNumber, vin);
        return carBooking;
    }
}
