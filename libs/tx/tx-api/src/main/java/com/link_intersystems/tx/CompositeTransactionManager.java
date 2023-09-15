package com.link_intersystems.tx;

import java.util.ArrayList;
import java.util.List;

public class CompositeTransactionManager implements TransactionManager {

    static class CompositeTransaction extends Transaction {

        private List<Transaction> transactions = new ArrayList<>();

        public CompositeTransaction(List<Transaction> transactions) {
            this.transactions.addAll(transactions);
        }

        @Override
        protected void rollback() throws Exception {
            Exception exceptions = new Exception();

            for (Transaction transaction : transactions) {
                try {
                    transaction.rollback();
                } catch (Exception e) {
                    exceptions.addSuppressed(e);
                }
            }

            if (exceptions.getSuppressed().length > 0) {
                throw exceptions;
            }
        }

        @Override
        protected void commit() throws Exception {
            Exception exceptions = new Exception();

            for (Transaction transaction : transactions) {
                try {
                    transaction.commit();
                } catch (Exception e) {
                    exceptions.addSuppressed(e);
                }
            }

            if (exceptions.getSuppressed().length > 0) {
                throw exceptions;
            }
        }
    }

    private List<TransactionManager> transactionManagers = new ArrayList<>();

    public CompositeTransactionManager(List<TransactionManager> transactionManagers) {
        this.transactionManagers.addAll(transactionManagers);
    }

    @Override
    public Transaction beginTransaction() throws Exception {
        List<Transaction> transactions = new ArrayList<>();

        Exception exceptions = new Exception();
        for (TransactionManager transactionManager : transactionManagers) {
            try {
                Transaction transaction = transactionManager.beginTransaction();
                transactions.add(transaction);
            } catch (Exception e) {
                exceptions.addSuppressed(e);
                break;
            }
        }

        CompositeTransaction compositeTransaction = new CompositeTransaction(transactions);

        if (exceptions.getSuppressed().length > 0) {
            try {

                compositeTransaction.rollback();
            } catch (Exception e) {
                exceptions.addSuppressed(e);
            }

            throw exceptions;
        }

        return compositeTransaction;
    }
}
