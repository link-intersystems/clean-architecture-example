package com.link_intersystems.carrental.offer;

import com.link_intersystems.carrental.main.AOPConfig;
import com.link_intersystems.jdbc.JdbcTemplate;

public class JdbcCarOfferComponent extends CarOfferComponent {

    private JdbcTemplate bookingJdbcTemplate;

    public JdbcCarOfferComponent(AOPConfig aopConfig, JdbcTemplate bookingJdbcTemplate) {
        super(aopConfig);
        this.bookingJdbcTemplate = bookingJdbcTemplate;
    }

    @Override
    protected CarOfferRepository getCarOfferRepository() {
        return new H2CarOfferRepository(bookingJdbcTemplate);
    }
}
