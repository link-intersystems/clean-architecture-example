package com.link_intersystems.carrental.management.booking.list;

import org.springframework.jdbc.core.JdbcTemplate;

public class H2ListBookingsRepositoryConfig {

    public ListBookingsRepository getListBookingsRepository(JdbcTemplate managementJdbcTemplate) {
        return new H2ListBookingsRepository(managementJdbcTemplate);
    }


}
