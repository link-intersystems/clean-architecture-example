package com.link_intersystems.jdbc.tx;

import com.link_intersystems.jdbc.AbstractConnectionDelegate;

import java.sql.Connection;
import java.sql.SQLException;

import static java.util.Objects.*;

public class LocalTransaction extends Transaction {

    private Connection connection;
    private TransactionListener transactionListener;

    public LocalTransaction(Connection connection) {
        this.connection = requireNonNull(connection);
        try {
            this.connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public TransactionListener getTransactionListener() {
        if (transactionListener == null) {
            return TransactionListener.NULL_INSTANCE;
        }
        return transactionListener;
    }

    public void setTransactionListener(TransactionListener transactionListener) {
        this.transactionListener = transactionListener;
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
    public void rollback() throws Exception {
        connection.rollback();
        getTransactionListener().rollback();
        connection.close();
        connection = null;
    }

    @Override
    public void commit() throws Exception {
        getTransactionListener().beforeCommit();
        connection.commit();
        getTransactionListener().afterCommit();
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
