package com.link_intersystems.carrental.management.rental.pickup.list;

import org.springframework.jdbc.core.JdbcTemplate;

public class H2ListPickupCarRepositoryConfig {

    public ListPickupCarRepository getListPickupCarRepository(JdbcTemplate managementJdbcTemplate) {
        return new H2ListPickupCarRepository(managementJdbcTemplate);
    }
}
