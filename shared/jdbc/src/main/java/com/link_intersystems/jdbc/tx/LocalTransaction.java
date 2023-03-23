package com.link_intersystems.jdbc.tx;

import com.link_intersystems.jdbc.AbstractConnectionDelegate;

import java.sql.Connection;
import java.sql.SQLException;

import static java.util.Objects.*;

public class LocalTransaction implements Transaction {

    private Connection connection;

    public LocalTransaction(Connection connection) {
        this.connection = requireNonNull(connection);
        try {
            this.connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() {
        class LocalTransactionConnection extends AbstractConnectionDelegate {

            public LocalTransactionConnection(Connection connection) {
                super(connection);
            }

            @Override
            public void setAutoCommit(boolean autoCommit) throws SQLException {
            }

            @Override
            public void close() throws SQLException {
            }
        }

        return new LocalTransactionConnection(connection);
    }

    @Override
    public void rollback() {
        try {
            connection.rollback();
            connection.close();
            connection = null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void commit() {
        try {
            connection.commit();
            connection.close();
            connection = null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T unwrap(Class<T> type) {
        if (Connection.class.isAssignableFrom(type)) {
            return type.cast(getConnection());
        }
        return Transaction.super.unwrap(type);
    }
}
