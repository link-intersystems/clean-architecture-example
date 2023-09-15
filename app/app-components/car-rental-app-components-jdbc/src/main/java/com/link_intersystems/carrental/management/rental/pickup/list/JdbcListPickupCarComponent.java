package com.link_intersystems.carrental.management.rental.pickup.list;


import com.link_intersystems.carrental.components.AOPConfig;
import com.link_intersystems.jdbc.JdbcTemplate;

public class JdbcListPickupCarComponent extends ListPickupCarComponent {

    private final JdbcTemplate managementJdbcTemplate;

    public JdbcListPickupCarComponent(AOPConfig aopConfig, JdbcTemplate managementJdbcTemplate) {
        super(aopConfig);
        this.managementJdbcTemplate = managementJdbcTemplate;
    }

    @Override
    protected ListPickupCarRepository getRepository() {
        return new JdbcListPickupCarRepository(managementJdbcTemplate);
    }
}