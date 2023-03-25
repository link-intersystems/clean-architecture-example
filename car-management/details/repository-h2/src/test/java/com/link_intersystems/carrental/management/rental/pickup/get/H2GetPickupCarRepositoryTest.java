package com.link_intersystems.carrental.management.rental.pickup.get;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.CarManagementDBExtension;
import com.link_intersystems.carrental.management.rental.CarRental;
import com.link_intersystems.jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

@CarManagementDBExtension
class H2GetPickupCarRepositoryIntTest  {

    private H2GetPickupCarRepository repository;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp(Connection connection) {
        jdbcTemplate = new JdbcTemplate(() -> connection);
        repository = new H2GetPickupCarRepository(jdbcTemplate);
    }

    @Test
    void find() {
        CarRental carRental = repository.find(new BookingNumber(2));

        assertNotNull(carRental);
    }
}