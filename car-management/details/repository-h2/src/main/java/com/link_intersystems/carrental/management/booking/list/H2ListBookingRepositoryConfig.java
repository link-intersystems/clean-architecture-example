package com.link_intersystems.carrental.management.booking.list;

import com.link_intersystems.app.context.BeanSelector;
import org.springframework.jdbc.core.JdbcTemplate;

public class H2ListBookingRepositoryConfig {

    public ListBookingsRepository getListBookingsRepository(BeanSelector<JdbcTemplate> beanSelector) {
        return new H2ListBookingsRepository(beanSelector.select("getManagementJdbcTemplate"));
    }


}
