package com.link_intersystems.tx.jdbc;

import com.link_intersystems.sql.AbstractDataSource;
import com.link_intersystems.tx.Transaction;
import com.link_intersystems.tx.TransactionManager;
import com.link_intersystems.tx.TransactionTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionAwareDataSourceTest {

    private TransactionTemplate transactionTemplate;
    private TransactionAwareDataSource transactionAwareDataSource;
    private MockConnection transactionConnection;

    @BeforeEach
    void setUp() {
        transactionConnection = new MockConnection();

        TransactionManager transactionManager = new TransactionManager() {
            @Override
            public Transaction beginTransaction() throws Exception {
                return new Transaction() {
                    @Override
                    protected void rollback() throws Exception {

                    }

                    @Override
                    protected void commit() throws Exception {

                    }

                    @Override
                    public <T> T unwrap(Class<T> type) {
                        if (Connection.class.isAssignableFrom(type)) {
                            return type.cast(transactionConnection);
                        }
                        return super.unwrap(type);
                    }
                };
            }
        };

        transactionTemplate = new TransactionTemplate(transactionManager);

        transactionAwareDataSource = new TransactionAwareDataSource(new AbstractDataSource() {
            @Override
            public Connection getConnection() throws SQLException {
                return new MockConnection();
            }

            @Override
            public Connection getConnection(String username, String password) throws SQLException {
                return new MockConnection();
            }
        });
    }

    @Test
    void getConnection() throws Exception {
        transactionTemplate.doInTransaction(() -> {
            Connection connection = transactionAwareDataSource.getConnection();
            assertEquals(transactionConnection, connection);
            return null;
        });

    }

    @Test
    void getConnectionByUsernameAndPassword() throws Exception {
        transactionTemplate.doInTransaction(() -> {
            Connection connection = transactionAwareDataSource.getConnection("", "");
            assertEquals(transactionConnection, connection);
            return null;
        });
    }
}