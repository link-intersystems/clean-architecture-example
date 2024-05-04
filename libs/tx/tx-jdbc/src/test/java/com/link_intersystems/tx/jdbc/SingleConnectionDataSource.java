package com.link_intersystems.tx.jdbc;

import com.link_intersystems.sql.AbstractDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static java.util.Objects.requireNonNull;

public class SingleConnectionDataSource extends AbstractDataSource {
    private final Connection connection;

    public SingleConnectionDataSource(Connection connection) {
        this.connection = requireNonNull(connection);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return connection;
    }
}
