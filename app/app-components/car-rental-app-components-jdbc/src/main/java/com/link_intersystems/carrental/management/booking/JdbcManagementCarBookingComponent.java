package com.link_intersystems.carrental.management.booking;


import com.link_intersystems.carrental.components.AOPConfig;
import com.link_intersystems.carrental.management.UpdateCarBookingRentalRepository;
import com.link_intersystems.jdbc.JdbcTemplate;

public class JdbcManagementCarBookingComponent extends ManagementCarBookingComponent {


    private final JdbcTemplate managementJdbcTemplate;

    public JdbcManagementCarBookingComponent(AOPConfig aopConfig, JdbcTemplate managementJdbcTemplate) {
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

    @Override
    protected ListBookingsRepository getListBookingsRepository() {
        return new JdbcListBookingsRepository(managementJdbcTemplate);
    }
}
