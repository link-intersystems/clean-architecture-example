package com.link_intersystems.carrental;

import com.link_intersystems.jdbc.tx.TransactionManager;
import com.link_intersystems.jdbc.tx.TransactionProxy;

public class AOPConfig {

    private TransactionConfig transactionConfig;

    public AOPConfig(TransactionConfig transactionConfig) {
        this.transactionConfig = transactionConfig;
    }

    public <T> T applyAOP(T bean) {
        TransactionManager tm = transactionConfig.getTransactionManager();
        return TransactionProxy.create(bean, tm);
    }
}
