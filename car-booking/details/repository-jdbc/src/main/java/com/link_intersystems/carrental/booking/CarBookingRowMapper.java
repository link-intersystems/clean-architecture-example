package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.customer.CustomerId;
import com.link_intersystems.carrental.time.Period;
import com.link_intersystems.jdbc.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

class CarBookingRowMapper implements RowMapper<CarBooking> {
    @Override
    public CarBooking mapRow(ResultSet rs) throws SQLException {
        CustomerId customerId = new CustomerId(rs.getInt("CUSTOMER_ID"));
        CarId carId = new CarId(new VIN(rs.getString("CARID")));

        Timestamp pickupDateTime = rs.getTimestamp("PICKUP_DATETIME");
        Timestamp returnDateTime = rs.getTimestamp("RETURN_DATETIME");

        Period bookingPeriod = new Period(pickupDateTime.toLocalDateTime(), returnDateTime.toLocalDateTime());
        return new CarBooking(customerId, carId, bookingPeriod);
    }
}