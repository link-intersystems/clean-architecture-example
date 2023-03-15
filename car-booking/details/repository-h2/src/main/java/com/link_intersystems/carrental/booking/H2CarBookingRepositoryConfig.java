package com.link_intersystems.carrental.booking;

import com.link_intersystems.ioc.api.BeanSelector;
import org.springframework.jdbc.core.JdbcTemplate;

public class H2CarBookingRepositoryConfig {

    public CarBookingRepository getCarBookingRepository(BeanSelector<JdbcTemplate> beanSelector) {
        return new H2CarBookingRepository(beanSelector.select("getCarRentalJdbcTemplate"));
    }

}
