package com.link_intersystems.carrental.management.booking.create;

import com.link_intersystems.app.context.BeanSelector;
import org.springframework.jdbc.core.JdbcTemplate;

public class H2CreateCarBookingRepositoryConfig {

    public CreateCarBookingRepository getCreateCarBookingRepository(BeanSelector<JdbcTemplate> beanSelector) {
        return new H2CreateCarBookingRepository(beanSelector.select("getManagementJdbcTemplate"));
    }


}
