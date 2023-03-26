package com.link_intersystems.carrental.management.booking.list;

import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.booking.CarBooking;
import com.link_intersystems.carrental.management.booking.Customer;
import com.link_intersystems.jdbc.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

class H2ListBookingsRepository implements ListBookingsRepository {

    private JdbcTemplate jdbcTemplate;

    public H2ListBookingsRepository(JdbcTemplate managementJdbcTemplate) {
        this.jdbcTemplate = managementJdbcTemplate;
    }


    @Override
    public List<CarBooking> findBookings() {
        return jdbcTemplate.query("SELECT * FROM CAR_BOOKING WHERE RENTAL_STATE IS NULL", this::mapCarBookingRow);
    }

    private CarBooking mapCarBookingRow(ResultSet rs) throws SQLException {
        BookingNumber bookingNumber = new BookingNumber(rs.getInt("BOOKING_NUMBER"));
        VIN vin = new VIN(rs.getString("VIN"));
        String customerFirstname = rs.getString("CUSTOMER_FIRSTNAME");
        String customerLastname = rs.getString("CUSTOMER_LASTNAME");
        Customer customer = new Customer(customerFirstname, customerLastname);
        return new CarBooking(bookingNumber, vin, customer);
    }
}
