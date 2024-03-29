package com.link_intersystems.carrental.management.rental;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.CarManagementDBExtension;
import com.link_intersystems.jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static com.link_intersystems.carrental.time.LocalDateTimeUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@CarManagementDBExtension
class JdbcGetPickupCarRepositoryIntTest {

    private JdbcGetPickupCarRepository repository;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp(Connection connection) {
        jdbcTemplate = new JdbcTemplate(() -> connection);
        repository = new JdbcGetPickupCarRepository(jdbcTemplate);
    }

    @Test
    void find() {
        CarRental carRental = repository.find(new BookingNumber(2));

        assertNotNull(carRental);
        assertEquals(new BookingNumber(2), carRental.getBookingNumber());
        Driver expectedDriver = new Driver("René", "Link", "ABC");
        assertEquals(expectedDriver, carRental.getDriver());
        assertEquals(dateTime("2023-01-19", "08:00:00"), carRental.getPickupDateTime());
    }
}