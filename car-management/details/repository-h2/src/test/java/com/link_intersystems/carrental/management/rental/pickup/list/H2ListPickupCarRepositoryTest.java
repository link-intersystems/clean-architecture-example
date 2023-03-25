package com.link_intersystems.carrental.management.rental.pickup.list;

import com.link_intersystems.carrental.management.CarManagementDBExtension;
import com.link_intersystems.carrental.management.rental.CarRental;
import com.link_intersystems.jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@CarManagementDBExtension
class H2ListPickupCarRepositoryIntTest  {

    private H2ListPickupCarRepository repository;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp(Connection connection) {
        jdbcTemplate = new JdbcTemplate(() -> connection);
        repository = new H2ListPickupCarRepository(jdbcTemplate);
    }

    @Test
    void findAll() {
        List<CarRental> all = repository.findAll();

        assertEquals(1, all.size());
    }
}