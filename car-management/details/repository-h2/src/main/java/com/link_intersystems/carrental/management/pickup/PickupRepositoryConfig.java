package com.link_intersystems.carrental.management.pickup;

import com.link_intersystems.app.context.BeanSelector;
import org.springframework.jdbc.core.JdbcTemplate;

public class PickupRepositoryConfig {

    public PickupCarRepository getPickupCarRepository(BeanSelector<JdbcTemplate> jdbcTemplateBeanSelector) {
        return new H2PickupCarRepository(jdbcTemplateBeanSelector.select("getManagementJdbcTemplate"));
    }
}
