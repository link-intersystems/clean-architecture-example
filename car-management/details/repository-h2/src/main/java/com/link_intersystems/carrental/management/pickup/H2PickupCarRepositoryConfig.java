package com.link_intersystems.carrental.management.pickup;

import com.link_intersystems.ioc.api.BeanSelector;
import org.springframework.jdbc.core.JdbcTemplate;

public class H2PickupCarRepositoryConfig {

    public PickupCarRepository getPickupCarRepository(BeanSelector<JdbcTemplate> jdbcTemplateBeanSelector) {
        return new H2PickupCarRepository(jdbcTemplateBeanSelector.select("getManagementJdbcTemplate"));
    }
}
