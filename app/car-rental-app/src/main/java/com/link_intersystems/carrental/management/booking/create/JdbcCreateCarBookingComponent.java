package com.link_intersystems.carrental.management.booking.create;


import com.link_intersystems.carrental.main.AOPConfig;
import com.link_intersystems.jdbc.JdbcTemplate;

public class JdbcCreateCarBookingComponent extends CreateCarBookingComponent {


    private final JdbcTemplate managementJdbcTemplate;

    public JdbcCreateCarBookingComponent(AOPConfig aopConfig, JdbcTemplate managementJdbcTemplate) {
        super(aopConfig);
        this.managementJdbcTemplate = managementJdbcTemplate;
    }

    @Override
    protected H2CreateCarBookingRepository getRepository() {
        return new H2CreateCarBookingRepository(managementJdbcTemplate);
    }
}
