package com.link_intersystems.carrental.management;

import com.link_intersystems.sql.AbstractDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;

@CarManagementDBExtension
public class AbstractManagementRepositoryTest {
    protected JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUpJdbcTemplate(Connection connection) {
        jdbcTemplate = new JdbcTemplate(new AbstractDataSource() {
            @Override
            public Connection getConnection() throws SQLException {
                return connection;
            }

            @Override
            public Connection getConnection(String username, String password) throws SQLException {
                return connection;
            }
        });
    }
}
