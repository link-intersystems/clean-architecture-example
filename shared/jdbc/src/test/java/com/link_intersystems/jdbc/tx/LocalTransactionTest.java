package com.link_intersystems.jdbc.tx;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class LocalTransactionTest {

    private MockConnection mockConnection;
    private LocalTransaction localTransaction;

    @BeforeEach
    void setUp() {
        mockConnection = new MockConnection();
        localTransaction = new LocalTransaction(mockConnection);
    }

    @Test
    void rollback() throws Exception {
        localTransaction.rollback();

        mockConnection.assertRollback();
    }

    @Test
    void commit() throws Exception {
        localTransaction.commit();

        mockConnection.assertCommit();
    }

    @Test
    void setAutoCommit() throws SQLException {
        mockConnection.setAutoCommit(true);

        Connection connection = localTransaction.unwrap(Connection.class);
        connection.setAutoCommit(false);

        assertTrue(connection.getAutoCommit());
    }
}