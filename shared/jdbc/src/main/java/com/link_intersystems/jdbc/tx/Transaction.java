package com.link_intersystems.jdbc.tx;

public interface Transaction {

    void rollback();

    void commit();

    default public <T> T unwrap(Class<T> type) {
        return null;
    }
}
