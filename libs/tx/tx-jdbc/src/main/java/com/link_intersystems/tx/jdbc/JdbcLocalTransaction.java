package com.link_intersystems.tx.jdbc;

import com.link_intersystems.jdbc.AbstractConnectionDelegate;
import com.link_intersystems.tx.Transaction;

import java.sql.Connection;
import java.sql.SQLException;

import static java.util.Objects.*;

public class JdbcLocalTransaction extends Transaction {

    private Connection connection;

    public JdbcLocalTransaction(Connection connection) {
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
            public void setAutoCommit(boolean autoCommit) {
            }

            @Override
            public void close() {
            }
        }

        return new LocalTransactionConnection(connection);
    }

    @Override
    public void rollback() throws Exception {
        connection.rollback();
        connection.close();
        connection = null;
    }

    @Override
    public void commit() throws Exception {
        connection.commit();
        connection.close();
        connection = null;
    }

    @Override
    public <T> T unwrap(Class<T> type) {
        if (Connection.class.isAssignableFrom(type)) {
            return type.cast(getConnection());
        }
        return super.unwrap(type);
    }
}
