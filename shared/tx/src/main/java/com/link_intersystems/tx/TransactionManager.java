package com.link_intersystems.tx;

public interface TransactionManager {
    Transaction beginTransaction() throws Exception;
}
