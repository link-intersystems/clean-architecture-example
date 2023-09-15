package com.link_intersystems.carrental.management.booking.create;


import com.link_intersystems.carrental.components.AOPConfig;
import com.link_intersystems.carrental.management.UpdateCarBookingRentalRepository;
import com.link_intersystems.carrental.management.booking.JdbcUpdateCarBookingRentalRepository;
import com.link_intersystems.jdbc.JdbcTemplate;

public class JdbcCreateCarBookingComponent extends CreateCarBookingComponent {


    private final JdbcTemplate managementJdbcTemplate;

    public JdbcCreateCarBookingComponent(AOPConfig aopConfig, JdbcTemplate managementJdbcTemplate) {
        super(aopConfig);
        this.managementJdbcTemplate = managementJdbcTemplate;
    }

    @Override
    protected CreateCarBookingRepository getCreateCarBookingRepository() {
        return new JdbcCreateCarBookingRepository(managementJdbcTemplate);
    }

    @Override
    protected UpdateCarBookingRentalRepository getUpdateCarBookingRentalRepository() {
        return new JdbcUpdateCarBookingRentalRepository(managementJdbcTemplate);
    }
}
