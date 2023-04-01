package com.link_intersystems.jdbc.tx;

class TransactionManagerMock implements TransactionManager {
    @Override
    public Transaction beginTransaction() throws Exception {
        return null;
    }
}
