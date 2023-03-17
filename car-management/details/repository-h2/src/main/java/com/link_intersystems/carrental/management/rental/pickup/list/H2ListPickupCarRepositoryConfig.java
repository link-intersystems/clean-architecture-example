package com.link_intersystems.carrental.management.rental.pickup.list;

import com.link_intersystems.ioc.api.BeanSelector;
import org.springframework.jdbc.core.JdbcTemplate;

public class H2ListPickupCarRepositoryConfig {

    public ListPickupCarRepository getListPickupCarRepository(BeanSelector<JdbcTemplate> jdbcTemplateBeanSelector) {
        return new H2ListPickupCarRepository(jdbcTemplateBeanSelector.select("getManagementJdbcTemplate"));
    }
}
