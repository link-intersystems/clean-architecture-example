package com.link_intersystems.carrental.booking;

import com.link_intersystems.app.context.BeanSelector;
import com.link_intersystems.carrental.DomainEventBusTemplate;
import com.link_intersystems.carrental.DomainEventSubscriber;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class H2CarBookingRepositoryConfig {

    public CarBookingRepository getCarBookingRepository(BeanSelector<JdbcTemplate> beanSelector) {
        return new H2CarBookingRepository(beanSelector.select("getCarRentalJdbcTemplate"));
    }

}
