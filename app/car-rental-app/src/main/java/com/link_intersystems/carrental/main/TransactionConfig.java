package com.link_intersystems.carrental.main;

import com.link_intersystems.tx.TransactionManager;
import jakarta.persistence.EntityManagerFactory;

import static java.util.Objects.*;

public class TransactionConfig {

    private DataSourceConfig dataSourceConfig;
    private final JpaConfig jpaConfig;
    private TransactionManager transactionManager;

    public TransactionConfig(DataSourceConfig dataSourceConfig, JpaConfig jpaConfig) {
        this.dataSourceConfig = requireNonNull(dataSourceConfig);
        this.jpaConfig = requireNonNull(jpaConfig);
    }

    public TransactionManager getTransactionManager() {
        if (transactionManager == null) {
            EntityManagerFactory entityManagerFactory = jpaConfig.getEntityManagerFactory();
            transactionManager = new JpaLocalTransactionManager(entityManagerFactory);
        }

        return transactionManager;
    }
}
