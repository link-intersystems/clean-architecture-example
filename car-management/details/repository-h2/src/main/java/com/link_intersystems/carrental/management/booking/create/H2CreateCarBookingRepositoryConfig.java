package com.link_intersystems.carrental.management.booking.create;

import org.springframework.jdbc.core.JdbcTemplate;

public class H2CreateCarBookingRepositoryConfig {

    public CreateCarBookingRepository getCreateCarBookingRepository(JdbcTemplate managementJdbcTemplate) {
        return new H2CreateCarBookingRepository(managementJdbcTemplate);
    }


}
