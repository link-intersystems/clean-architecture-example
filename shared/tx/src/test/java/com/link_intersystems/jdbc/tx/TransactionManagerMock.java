package com.link_intersystems.jdbc.tx;

import com.link_intersystems.tx.Transaction;
import com.link_intersystems.tx.TransactionManager;

class TransactionManagerMock implements TransactionManager {
    @Override
    public Transaction beginTransaction() throws Exception {
        return null;
    }
}
