package com.link_intersystems.jdbc.tx;

public interface TransactionListener {

    public static final TransactionListener NULL_INSTANCE = new TransactionListener() {
    };

    default void begin() {
    }

    default void rollback() {
    }

    default void beforeCommit() {
    }

    default void afterCommit() {
    }
}
