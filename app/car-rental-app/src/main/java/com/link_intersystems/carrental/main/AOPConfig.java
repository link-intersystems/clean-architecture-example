package com.link_intersystems.carrental.main;

import com.link_intersystems.aop.MethodInterceptorProxy;
import com.link_intersystems.tx.TransactionManager;
import com.link_intersystems.tx.TransactionMethodInterceptor;

public class AOPConfig {

    private TransactionConfig transactionConfig;

    public AOPConfig(TransactionConfig transactionConfig) {
        this.transactionConfig = transactionConfig;
    }

    public <T, I extends T> T applyAOP(Class<T> type, I implementation) {
        TransactionManager tm = transactionConfig.getTransactionManager();
        TransactionMethodInterceptor transactionMethodInterceptor = new TransactionMethodInterceptor(tm);
        return new MethodInterceptorProxy.Builder().withInterceptor(transactionMethodInterceptor).build(type, implementation);
    }
}
