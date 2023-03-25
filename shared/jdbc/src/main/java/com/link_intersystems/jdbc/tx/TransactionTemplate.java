package com.link_intersystems.jdbc.tx;

import java.util.concurrent.Callable;

import static java.util.Objects.*;

public class TransactionTemplate {

    private TransactionManager transactionManager;

    public TransactionTemplate(TransactionManager transactionManager) {
        this.transactionManager = requireNonNull(transactionManager);
    }

    public <T> T doInTransaction(Callable<T> callable) throws Exception {
        Transaction currentTransaction = Transaction.getCurrent();

        Transaction transaction = currentTransaction;

        if (transaction == null) {
            transaction = requireNonNull(transactionManager.beginTransaction(), "transaction");
            Transaction.setCurrent(transaction);
        }

        try {
            T result = callable.call();
            if (currentTransaction == null) {
                safeCommit(transaction);
            }
            return result;
        } catch (Throwable e) {
            if (currentTransaction == null) {
                safeRollback(transaction);
            }
            throw e;
        }
    }

    private void safeCommit(Transaction transaction) throws Exception {
        try {
            transaction.commit();
        } finally {
            Transaction.setCurrent(null);
        }
    }

    private void safeRollback(Transaction transaction) throws Exception {
        try {
            transaction.rollback();
        } finally {
            Transaction.setCurrent(null);
        }
    }
}
