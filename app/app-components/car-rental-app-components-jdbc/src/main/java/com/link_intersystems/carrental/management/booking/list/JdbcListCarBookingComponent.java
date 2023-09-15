package com.link_intersystems.carrental.management.booking.list;


import com.link_intersystems.carrental.components.AOPConfig;
import com.link_intersystems.jdbc.JdbcTemplate;

public class JdbcListCarBookingComponent extends ListCarBookingComponent {

    private final JdbcTemplate managementJdbcTemplate;

    public JdbcListCarBookingComponent(AOPConfig aopConfig, JdbcTemplate managementJdbcTemplate) {
        super(aopConfig);
        this.managementJdbcTemplate = managementJdbcTemplate;
    }

    @Override
    protected ListBookingsRepository getRepository() {
        return new JdbcListBookingsRepository(managementJdbcTemplate);
    }
}
