package com.link_intersystems.jdbc.tx;

import com.link_intersystems.jdbc.ConnectionSupplier;

import javax.sql.DataSource;
import java.sql.Connection;

import static java.util.Objects.*;

public class LocalTransactionManager implements TransactionManager {

    private ConnectionSupplier connectionSupplier;

    private CompositeTransactionListener transactionListener = new CompositeTransactionListener();

    public LocalTransactionManager(DataSource dataSource) {
        this(dataSource::getConnection);
    }

    public LocalTransactionManager(ConnectionSupplier connectionSupplier) {
        this.connectionSupplier = requireNonNull(connectionSupplier);
    }

    @Override
    public Transaction beginTransaction() throws Exception {
        Connection connection = connectionSupplier.get();
        LocalTransaction localTransaction = new LocalTransaction(connection);
        localTransaction.setTransactionListener(transactionListener);
        transactionListener.begin();
        return localTransaction;
    }

    @Override
    public void addTransactionListener(TransactionListener transactionListener) {
        this.transactionListener.addTransactionListener(transactionListener);
    }
}
