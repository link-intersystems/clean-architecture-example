package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.components.AOPConfig;
import com.link_intersystems.jdbc.JdbcTemplate;

public class JdbcBookingComponent extends CarBookingComponent {

    private JdbcTemplate bookingJdbcTemplate;

    public JdbcBookingComponent(AOPConfig aopConfig, JdbcTemplate bookingJdbcTemplate) {
        super(aopConfig);
        this.bookingJdbcTemplate = bookingJdbcTemplate;
    }

    @Override
    protected CarBookingRepository getRepository() {
        return new JdbcCarBookingRepository(bookingJdbcTemplate);
    }
}
