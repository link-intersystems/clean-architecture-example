package com.link_intersystems.carrental.main;

import com.link_intersystems.tx.TransactionManager;
import com.link_intersystems.tx.jdbc.JdbcLocalTransactionManager;

import static java.util.Objects.*;

public class TransactionConfig {

    private DataSourceConfig dataSourceConfig;
    private TransactionManager transactionManager;

    public TransactionConfig(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = requireNonNull(dataSourceConfig);
    }

    public TransactionManager getTransactionManager() {
        if (transactionManager == null) {
            transactionManager = new JdbcLocalTransactionManager(dataSourceConfig.getDataSource());
        }

        return transactionManager;
    }
}
