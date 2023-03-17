package com.link_intersystems.carrental.management.booking.list;

import com.link_intersystems.ioc.api.BeanSelector;
import org.springframework.jdbc.core.JdbcTemplate;

public class H2ListBookingsRepositoryConfig {

    public ListBookingsRepository getListBookingsRepository(BeanSelector<JdbcTemplate> beanSelector) {
        return new H2ListBookingsRepository(beanSelector.select("getManagementJdbcTemplate"));
    }


}