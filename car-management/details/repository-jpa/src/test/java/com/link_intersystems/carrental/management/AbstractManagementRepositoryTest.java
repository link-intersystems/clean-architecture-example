package com.link_intersystems.carrental.management;

import com.link_intersystems.jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;

@CarManagementDBExtension
public class AbstractManagementRepositoryTest {
    protected JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUpJdbcTemplate(Connection connection) {
        jdbcTemplate = new JdbcTemplate(() -> connection);
    }
}
