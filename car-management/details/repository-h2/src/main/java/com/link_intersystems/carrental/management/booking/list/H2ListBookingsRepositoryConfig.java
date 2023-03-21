package com.link_intersystems.carrental.management.booking.list;

import com.link_intersystems.jdbc.JdbcTemplate;

public class H2ListBookingsRepositoryConfig {

    public ListBookingsRepository getListBookingsRepository(JdbcTemplate managementJdbcTemplate) {
        return new H2ListBookingsRepository(managementJdbcTemplate);
    }


}
