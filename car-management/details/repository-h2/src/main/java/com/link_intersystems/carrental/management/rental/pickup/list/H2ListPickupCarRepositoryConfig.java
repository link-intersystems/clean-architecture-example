package com.link_intersystems.carrental.management.rental.pickup.list;

import com.link_intersystems.jdbc.JdbcTemplate;

public class H2ListPickupCarRepositoryConfig {

    public ListPickupCarRepository getListPickupCarRepository(JdbcTemplate managementJdbcTemplate) {
        return new H2ListPickupCarRepository(managementJdbcTemplate);
    }
}
