package com.link_intersystems.jdbc.tx;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.*;

public class CompositeTransactionListener implements TransactionListener {

    private List<TransactionListener> listeners = new ArrayList<>();

    public void addTransactionListener(TransactionListener transactionListener) {
        listeners.add(requireNonNull(transactionListener));
    }

    public void removeTransactionListenr(TransactionListener transactionListener){
        listeners.remove(transactionListener);
    }

    @Override
    public void begin() {
        listeners.forEach(TransactionListener::begin);
    }

    @Override
    public void rollback() {
        listeners.forEach(TransactionListener::rollback);
    }

    @Override
    public void beforeCommit() {
        listeners.forEach(TransactionListener::beforeCommit);
    }

    @Override
    public void afterCommit() {
        listeners.forEach(TransactionListener::afterCommit);
    }
}
