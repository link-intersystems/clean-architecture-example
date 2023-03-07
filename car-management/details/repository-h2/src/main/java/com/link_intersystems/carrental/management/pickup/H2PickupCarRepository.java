package com.link_intersystems.carrental.management.pickup;

import org.springframework.jdbc.core.JdbcTemplate;

public class H2PickupCarRepository implements PickupCarRepository {

    private JdbcTemplate jdbcTemplate;

    public H2PickupCarRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


}
