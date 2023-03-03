package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.customer.Customer;
import com.link_intersystems.carrental.customer.CustomerId;
import com.link_intersystems.time.Period;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class H2CarBookingRepository implements CarBookingRepository {

    private final JdbcTemplate jdbcTemplate;

    public H2CarBookingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public CarBooking findBooking(CarId carId, Period bookingPeriod) {
        try {
            String sql = "select * from car_booking where carid = ? " + " AND ((CAR_BOOKING.PICKUP_DATETIME >= ? OR CAR_BOOKING.PICKUP_DATETIME <= ?)" + " OR (CAR_BOOKING.RETURN_DATETIME >= ? OR CAR_BOOKING.RETURN_DATETIME <= ?))";
            LocalDateTime begin = bookingPeriod.getBegin();
            LocalDateTime end = bookingPeriod.getEnd();
            List<CarBooking> carBookings = jdbcTemplate.query(sql, new Object[]{carId.getValue(), begin, end, begin, end}, new RowMapper<CarBooking>() {
                @Override
                public CarBooking mapRow(ResultSet rs, int rowNum) throws SQLException {
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
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void persist(CarBooking carBooking) {
        CarId carId = carBooking.getCarId();
        CustomerId customerId = carBooking.getCustomerId();
        Period bookingPeriod = carBooking.getBookingPeriod();

        SimpleJdbcInsert carBookingInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("CAR_BOOKING").usingGeneratedKeyColumns("BOOKING_NUMBER");
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("CARID", carId.getValue());
        parameters.put("CUSTOMER_ID", customerId.getValue());
        parameters.put("PICKUP_DATETIME", bookingPeriod.getBegin());
        parameters.put("RETURN_DATETIME", bookingPeriod.getEnd());

        Number newId = carBookingInsert.executeAndReturnKey(parameters);

        carBooking.setBookingNumber(new BookingNumber(newId.intValue()));
    }

    @Override
    public boolean isCustomerExistent(CustomerId customerId) {
        try {
            Customer customer = jdbcTemplate.queryForObject("select * from customer where id = ?", new Object[]{customerId.getValue()}, new RowMapper<Customer>() {
                @Override
                public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    CustomerId customerId = new CustomerId(rs.getInt("ID"));

                    String firstname = rs.getString("firstname");
                    String lastname = rs.getString("lastname");

                    return new Customer(customerId, firstname, lastname);
                }
            });
            return customer != null;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
