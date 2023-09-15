package com.link_intersystems.jdbc;

import com.link_intersystems.sql.AbstractDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static java.util.Objects.*;

public class ConnectionSupplierDataSourceAdapter extends AbstractDataSource {

    private ConnectionSupplier connectionSupplier;

    public ConnectionSupplierDataSourceAdapter(ConnectionSupplier connectionSupplier) {
        this.connectionSupplier = requireNonNull(connectionSupplier);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return connectionSupplier.get();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getConnection();
    }
}
