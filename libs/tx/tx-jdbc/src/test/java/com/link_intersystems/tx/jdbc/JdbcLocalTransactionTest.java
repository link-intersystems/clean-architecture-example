package com.link_intersystems.tx.jdbc;

import com.link_intersystems.jdbc.AbstractConnectionDelegate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class JdbcLocalTransactionTest {

    private MockConnection mockConnection;
    private JdbcLocalTransaction jdbcLocalTransaction;

    @BeforeEach
    void setUp() {
        mockConnection = new MockConnection();
        jdbcLocalTransaction = new JdbcLocalTransaction(mockConnection);
    }

    @Test
    void exceptionOnInit() {
        SQLException sqlException = new SQLException("");

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            new JdbcLocalTransaction(new AbstractConnectionDelegate(new MockConnection()) {
                @Override
                public void setAutoCommit(boolean autoCommit) throws SQLException {

                    throw sqlException;
                }
            });
        });

        assertSame(runtimeException.getCause(), sqlException);
    }

    @Test
    void rollback() throws Exception {
        jdbcLocalTransaction.rollback();

        mockConnection.assertRollback();
    }

    @Test
    void commit() throws Exception {
        jdbcLocalTransaction.commit();

        mockConnection.assertCommit();
    }

    @Test
    void setAutoCommit() throws SQLException {
        mockConnection.setAutoCommit(true);

        Connection connection = jdbcLocalTransaction.unwrap(Connection.class);
        connection.setAutoCommit(false);

        assertTrue(connection.getAutoCommit());
    }

    @Test
    void unwrapUnknownType() throws SQLException {
        assertNull(jdbcLocalTransaction.unwrap(CharSequence.class));
    }
}