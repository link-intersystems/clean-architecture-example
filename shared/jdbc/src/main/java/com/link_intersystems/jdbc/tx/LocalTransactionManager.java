package com.link_intersystems.jdbc.tx;

import javax.sql.DataSource;
import java.sql.Connection;

public class LocalTransactionManager implements TransactionManager {

    private DataSource dataSource;

    public LocalTransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Transaction beginTransaction() throws Exception {
        Connection connection = dataSource.getConnection();
        return new LocalTransaction(connection);
    }
}
