package com.link_intersystems.carrental.management.rental.pickup;

import org.springframework.jdbc.core.JdbcTemplate;

public class H2PickupCarRepositoryConfig {

    public PickupCarRepository getPickupCarRepository(JdbcTemplate managementJdbcTemplate) {
        return new H2PickupCarRepository(managementJdbcTemplate);
    }
}
