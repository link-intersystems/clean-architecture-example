package com.link_intersystems.carrental.management.rental.pickup.get;

import com.link_intersystems.carrental.components.AOPConfig;
import com.link_intersystems.jdbc.JdbcTemplate;

public class JdbcGetPickupCarComponent extends GetPickupCarComponent {


    private JdbcTemplate managementJdbcTemplate;

    public JdbcGetPickupCarComponent(AOPConfig aopConfig, JdbcTemplate managementJdbcTemplate) {
        super(aopConfig);
        this.managementJdbcTemplate = managementJdbcTemplate;
    }

    @Override
    protected GetPickupCarRepository getRepository() {
        return new H2GetPickupCarRepository(managementJdbcTemplate);
    }
}
