package com.link_intersystems.carrental.offer;

import com.link_intersystems.carrental.components.AOPConfig;
import com.link_intersystems.jdbc.JdbcTemplate;

public class JdbcCarOfferComponent extends CarOfferComponent {

    private JdbcTemplate bookingJdbcTemplate;

    public JdbcCarOfferComponent(AOPConfig aopConfig, JdbcTemplate bookingJdbcTemplate) {
        super(aopConfig);
        this.bookingJdbcTemplate = bookingJdbcTemplate;
    }

    @Override
    protected CarOfferRepository getRepository() {
        return new JdbcCarOfferRepository(bookingJdbcTemplate);
    }
}