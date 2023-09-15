package com.link_intersystems.carrental.management.booking;

import com.link_intersystems.jdbc.JdbcTemplate;

public class TestJdbcCreateCarBookingRepository extends JdbcCreateCarBookingRepository {
    public TestJdbcCreateCarBookingRepository(JdbcTemplate managementJdbcTemplate) {
        super(managementJdbcTemplate);
    }
}
