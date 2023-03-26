package com.link_intersystems.carrental.main;

import com.link_intersystems.jdbc.tx.LocalTransactionManager;
import com.link_intersystems.jdbc.tx.TransactionAwareDataSource;
import com.link_intersystems.jdbc.tx.TransactionManager;

import javax.sql.DataSource;

import static java.util.Objects.*;

public class TransactionConfig {

    private DataSourceConfig dataSourceConfig;
    private LocalTransactionManager transactionManager;
    private TransactionAwareDataSource transactionAwareDataSource;

    public TransactionConfig(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = requireNonNull(dataSourceConfig);
    }

    public DataSource getTransactionAwareDataSource() {
        if (transactionAwareDataSource == null) {
            DataSource dataSource = dataSourceConfig.getDataSource();
            transactionAwareDataSource = new TransactionAwareDataSource(dataSource);
        }

        return transactionAwareDataSource;
    }

    public TransactionManager getTransactionManager() {
        if (transactionManager == null) {
            DataSource dataSource = dataSourceConfig.getDataSource();
            transactionManager = new LocalTransactionManager(dataSource);
        }

        return transactionManager;
    }
}
