package com.link_intersystems.tx.jdbc;

import com.link_intersystems.jdbc.ConnectionSupplier;
import com.link_intersystems.tx.Transaction;
import com.link_intersystems.tx.TransactionManager;

import javax.sql.DataSource;
import java.sql.Connection;

import static java.util.Objects.*;

public class JdbcLocalTransactionManager implements TransactionManager {

    private ConnectionSupplier connectionSupplier;

    public JdbcLocalTransactionManager(DataSource dataSource) {
        this(dataSource::getConnection);
    }

    public JdbcLocalTransactionManager(ConnectionSupplier connectionSupplier) {
        this.connectionSupplier = requireNonNull(connectionSupplier);
    }

    @Override
    public Transaction beginTransaction() throws Exception {
        Connection connection = connectionSupplier.get();
        return new JdbcLocalTransaction(connection);
    }

}
