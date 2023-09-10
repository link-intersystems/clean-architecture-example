package com.link_intersystems.carrental.management.rental.returnCar;

import com.link_intersystems.carrental.main.AOPConfig;
import com.link_intersystems.jdbc.JdbcTemplate;

public class JdbcReturnCarComponent extends ReturnCarComponent {

    private final JdbcTemplate managementJdbcTemplate;

    public JdbcReturnCarComponent(AOPConfig aopConfig, JdbcTemplate managementJdbcTemplate) {
        super(aopConfig);
        this.managementJdbcTemplate = managementJdbcTemplate;
    }

    @Override
    protected ReturnCarRepository getRepository() {
        return new H2ReturnCarRepository(managementJdbcTemplate);
    }
}
