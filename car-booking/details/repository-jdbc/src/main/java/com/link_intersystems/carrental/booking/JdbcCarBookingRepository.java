package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.customer.Customer;
import com.link_intersystems.carrental.customer.CustomerId;
import com.link_intersystems.carrental.time.Period;
import com.link_intersystems.jdbc.JdbcTemplate;
import com.link_intersystems.jdbc.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

class JdbcCarBookingRepository implements CarBookingRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCarBookingRepository(JdbcTemplate carRentalJdbcTemplate) {
        this.jdbcTemplate = carRentalJdbcTemplate;
    }

    @Override
    public CarBooking findBooking(CarId carId, Period bookingPeriod) {
        String sql = "select * from car_booking where carid = ? " + " AND ((CAR_BOOKING.PICKUP_DATETIME >= ? OR CAR_BOOKING.PICKUP_DATETIME <= ?)" + " OR (CAR_BOOKING.RETURN_DATETIME >= ? OR CAR_BOOKING.RETURN_DATETIME <= ?))";
        LocalDateTime begin = bookingPeriod.getBegin();
        LocalDateTime end = bookingPeriod.getEnd();
        List<CarBooking> carBookings = jdbcTemplate.query(sql, new Object[]{carId.getValue(), begin, end, begin, end}, new RowMapper<CarBooking>() {
            @Override
            public CarBooking mapRow(ResultSet rs) throws SQLException {
                CustomerId customerId = new CustomerId(rs.getInt("CUSTOMER_ID"));
                CarId carId = new CarId(new VIN(rs.getString("CARID")));

                Timestamp pickupDateTime = rs.getTimestamp("PICKUP_DATETIME");
                Timestamp returnDateTime = rs.getTimestamp("RETURN_DATETIME");

                Period bookingPeriod = new Period(pickupDateTime.toLocalDateTime(), returnDateTime.toLocalDateTime());
                CarBooking carBooking = new CarBooking(customerId, carId, bookingPeriod);
                carBooking.setBookingNumber(new BookingNumber(rs.getInt("BOOKING_NUMBER")));
                return carBooking;
            }
        });

        List<CarBooking> overlappingCarBookings = carBookings.stream().filter(cb -> bookingPeriod.overlaps(cb.getBookingPeriod())).collect(Collectors.toList());
        return overlappingCarBookings.isEmpty() ? null : overlappingCarBookings.get(0);
    }

    @Override
    public void persist(CarBooking carBooking) {
        Integer nextId = jdbcTemplate.queryForObject("VALUES NEXT VALUE FOR CAR_BOOKING_SEQ", rs -> rs.getInt(1));

        CarId carId = carBooking.getCarId();
        CustomerId customerId = carBooking.getCustomerId();
        Period bookingPeriod = carBooking.getBookingPeriod();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO CAR_BOOKING(BOOKING_NUMBER, CARID, CUSTOMER_ID, PICKUP_DATETIME, RETURN_DATETIME) VALUES (?, ?, ? , ?, ?)");
            ps.setObject(1, nextId);
            ps.setObject(2, carId.getValue());
            ps.setObject(3, customerId.getValue());
            ps.setObject(4, bookingPeriod.getBegin());
            ps.setObject(5, bookingPeriod.getEnd());
            return ps.executeUpdate();
        });


        carBooking.setBookingNumber(new BookingNumber(nextId));
    }

    @Override
    public Customer findCustomer(CustomerId customerId) {
        Object[] queryArgs = {customerId.getValue()};
        Customer customer = jdbcTemplate.queryForObject("""
                select * from customer where id = ?""", queryArgs, this::map);
        return customer;
    }

    private Customer map(ResultSet rs) throws SQLException {
        CustomerId id = new CustomerId(rs.getInt("ID"));
        String firstname = rs.getString("FIRSTNAME");
        String lastname = rs.getString("LASTNAME");
        return new Customer(id, firstname, lastname);
    }
}
