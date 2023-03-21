package com.link_intersystems.carrental.management.booking.create;

import com.link_intersystems.jdbc.JdbcTemplate;

public class H2CreateCarBookingRepositoryConfig {

    public CreateCarBookingRepository getCreateCarBookingRepository(JdbcTemplate managementJdbcTemplate) {
        return new H2CreateCarBookingRepository(managementJdbcTemplate);
    }


}
