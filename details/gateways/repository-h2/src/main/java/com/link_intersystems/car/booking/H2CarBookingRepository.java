package com.link_intersystems.car.booking;

import com.link_intersystems.car.CarId;
import com.link_intersystems.car.VIN;
import com.link_intersystems.person.customer.Customer;
import com.link_intersystems.person.customer.CustomerId;
import com.link_intersystems.sql.io.InputStreamScriptResource;
import com.link_intersystems.sql.io.SqlScript;
import com.link_intersystems.time.Period;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class H2CarBookingRepository implements CarBookingRepository {

    private final JdbcTemplate jdbcTemplate;

    public H2CarBookingRepository() {
        StringBuilder urlBuilder = new StringBuilder("jdbc:h2:file:./car-rental");
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(urlBuilder.toString());
        jdbcDataSource.setUser("");
        jdbcDataSource.setPassword("");

        if (!new File("./car-rental.mv.db").exists()) {


            try (Connection connection = jdbcDataSource.getConnection()) {
                InputStream resourceAsStream = H2CarBookingRepository.class.getResourceAsStream("init.sql");
                SqlScript sqlScript = new SqlScript(new InputStreamScriptResource(resourceAsStream));
                sqlScript.execute(connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        jdbcTemplate = new JdbcTemplate(jdbcDataSource);
    }

    @Override
    public CarBooking findBooking(CarId carId, Period bookingPeriod) {
        try {
            CarBooking carBooking = jdbcTemplate.queryForObject("select * from car_booking where carid = ?", new Object[]{carId.getValue()}, new RowMapper<CarBooking>() {
                @Override
                public CarBooking mapRow(ResultSet rs, int rowNum) throws SQLException {
                    CustomerId customerId = new CustomerId(rs.getInt("CUSTOMER_ID"));
                    CarId carId = new CarId(new VIN(rs.getString("CARID")));

                    Timestamp pickupDateTime = rs.getTimestamp("PICKUP_DATETIME");
                    Timestamp returnDateTime = rs.getTimestamp("RETURN_DATETIME");

                    Period bookingPeriod = new Period(pickupDateTime.toLocalDateTime(), returnDateTime.toLocalDateTime());
                    return new CarBooking(customerId, carId, bookingPeriod);
                }
            });

            return carBooking;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void persist(CarBooking carBooking) {

    }

    @Override
    public boolean isCustomerExistent(CustomerId customerId) {
        try {
            Customer customer = jdbcTemplate.queryForObject("select * from customer where id = ?", new Object[]{customerId.getValue()}, new RowMapper<Customer>() {
                @Override
                public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    CustomerId customerId = new CustomerId(rs.getInt("ID"));

                    String firstname = rs.getString("FISTNAME");
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
