package com.link_intersystems.jdbc.tx;

public interface TransactionManager {
    Transaction beginTransaction() throws Exception;

    void addTransactionListener(TransactionListener transactionListener);
}
