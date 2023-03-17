package com.link_intersystems.carrental.management.rental.pickup.get;

import com.link_intersystems.ioc.api.BeanSelector;
import org.springframework.jdbc.core.JdbcTemplate;

public class H2GetPickupCarRepositoryConfig {

    public GetPickupCarRepository getGetPickupCarRepository(BeanSelector<JdbcTemplate> jdbcTemplateBeanSelector) {
        return new H2GetPickupCarRepository(jdbcTemplateBeanSelector.select("getManagementJdbcTemplate"));
    }
}