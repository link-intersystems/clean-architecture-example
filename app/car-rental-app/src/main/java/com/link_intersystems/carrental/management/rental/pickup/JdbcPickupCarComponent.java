package com.link_intersystems.carrental.management.rental.pickup;

import com.link_intersystems.carrental.main.AOPConfig;
import com.link_intersystems.jdbc.JdbcTemplate;

public class JdbcPickupCarComponent extends PickupCarComponent {

    private final JdbcTemplate managementJdbcTemplate;

    public JdbcPickupCarComponent(AOPConfig aopConfig, JdbcTemplate managementJdbcTemplate) {
        super(aopConfig);
        this.managementJdbcTemplate = managementJdbcTemplate;
    }

    @Override
    protected PickupCarRepository getRepository() {
        return new H2PickupCarRepository(managementJdbcTemplate);
    }
}
