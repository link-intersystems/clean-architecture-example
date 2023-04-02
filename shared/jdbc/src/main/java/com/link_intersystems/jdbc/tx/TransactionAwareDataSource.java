package com.link_intersystems.jdbc.tx;

import com.link_intersystems.sql.AbstractDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionAwareDataSource extends AbstractDataSource {

    private DataSource dataSource;

    public TransactionAwareDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        Transaction currentTransaction = Transaction.getCurrent();

        if (currentTransaction == null) {
            return dataSource.getConnection();
        }

        return currentTransaction.unwrap(Connection.class);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getConnection();
    }

}
