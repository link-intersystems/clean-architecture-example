package com.link_intersystems.jdbc.tx;

import com.link_intersystems.jdbc.ConnectionSupplier;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Objects;

import static java.util.Objects.*;

public class LocalTransactionManager implements TransactionManager {

    private ConnectionSupplier connectionSupplier;

    public LocalTransactionManager(DataSource dataSource) {
        this(dataSource::getConnection);
    }

    public LocalTransactionManager(ConnectionSupplier connectionSupplier) {
        this.connectionSupplier = requireNonNull(connectionSupplier);
    }

    @Override
    public Transaction beginTransaction() throws Exception {
        Connection connection = connectionSupplier.get();
        return new LocalTransaction(connection);
    }
}
