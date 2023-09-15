package com.link_intersystems.carrental.management.booking;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.UpdateCarBookingRentalRepository;
import com.link_intersystems.jdbc.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.Map;

public class JdbcUpdateCarBookingRentalRepository implements UpdateCarBookingRentalRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcUpdateCarBookingRentalRepository(JdbcTemplate managementJdbcTemplate) {
        this.jdbcTemplate = managementJdbcTemplate;
    }

    @Override
    public CarBooking findCarBooking(BookingNumber bookingNumber) {
        Map<String, Object> row = jdbcTemplate.queryForMap("""
                SELECT * FROM CAR_BOOKING 
                    WHERE
                     BOOKING_NUMER = ?
                     """, bookingNumber.getValue());
        return new CarBookingFactory().create(row);
    }

    @Override
    public void persist(CarBooking carBooking) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("""
                    MERGE INTO CAR_BOOKING (
                        VIN, 
                        BOOKING_NUMBER, 
                        RENTAL_STATE,
                        CUSTOMER_FIRSTNAME,
                        CUSTOMER_LASTNAME
                        ) 
                        KEY(BOOKING_NUMBER) 
                        VALUES (?, ? ,?, ?, ?)""");
            ps.setObject(1, carBooking.getVin().getValue());
            ps.setObject(2, carBooking.getBookingNumber().getValue());

            RentalState rentalState = carBooking.getRentalState();
            if (rentalState == null) {
                ps.setNull(3, Types.VARCHAR);
            } else {
                ps.setObject(3, rentalState.name());
            }

            Customer customer = carBooking.getCustomer();
            ps.setString(4, customer.getFirstname());
            ps.setString(5, customer.getLastname());

            return ps.executeUpdate();
        });
    }
}
