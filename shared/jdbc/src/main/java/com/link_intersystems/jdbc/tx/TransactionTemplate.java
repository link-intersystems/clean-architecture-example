package com.link_intersystems.jdbc.tx;

import java.util.concurrent.Callable;

import static java.util.Objects.*;

public class TransactionTemplate {

    private TransactionManager transactionManager;

    public TransactionTemplate(TransactionManager transactionManager) {
        this.transactionManager = requireNonNull(transactionManager);
    }

    public <T> T doInTransaction(Callable<T> callable) throws Exception {
        Transaction currentTransaction = TransactionHolder.getCurrentTransaction();

        Transaction transaction = currentTransaction;

        if (transaction == null) {
            transaction = transactionManager.beginTransaction();
            TransactionHolder.setCurrentTransaction(transaction);
        }

        try {
            T result = callable.call();
            if (currentTransaction == null) {
                transaction.commit();
                TransactionHolder.setCurrentTransaction(null);
            }
            return result;
        } catch (Throwable e) {
            if (currentTransaction == null) {
                transaction.rollback();
                TransactionHolder.setCurrentTransaction(null);
            }
            throw e;
        }
    }
}
