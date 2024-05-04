package com.link_intersystems.tx.jdbc;

import com.link_intersystems.jdbc.ConnectionSupplierDataSourceAdapter;
import com.link_intersystems.sql.AbstractDataSource;
import com.link_intersystems.tx.Transaction;
import com.link_intersystems.tx.TransactionManager;
import com.link_intersystems.tx.TransactionTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class TransactionComponentTest {

    private TransactionTemplate transactionTemplate;
    private TransactionAwareDataSource transactionAwareDataSource;
    private MockConnection connection;
    private TransactionManager transactionManager;

    @BeforeEach
    void setUp() {
        connection = new MockConnection();
        SingleConnectionDataSource singleConnectionDataSource = new SingleConnectionDataSource(connection);
        transactionManager = new JdbcLocalTransactionManager(singleConnectionDataSource);
        transactionAwareDataSource = new TransactionAwareDataSource(new ConnectionSupplierDataSourceAdapter(() -> connection));

        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Test
    void closeConnectionHasNoEffect() throws Exception {
        Transaction transaction = transactionManager.beginTransaction();
        Connection connection = transaction.unwrap(Connection.class);
        connection.close();

        assertFalse(connection.isClosed());
    }

    @Test
    void noActiveTransaction() throws Exception {
        Connection connection = transactionAwareDataSource.getConnection();
        assertEquals(this.connection, connection);
    }

    @Test
    void doInTransaction() throws Exception {
        String result = transactionTemplate.doInTransaction(() -> {
            assertNotNull(Transaction.getCurrent());

            Connection connection1 = transactionAwareDataSource.getConnection();
            Connection connection2 = transactionAwareDataSource.getConnection();

            assertEquals(connection1, connection2);

            return "test";
        });

        assertEquals("test", result);
    }

    @Test
    void nestedTransactionTemplates() throws Exception {
        String result = transactionTemplate.doInTransaction(() -> {
            assertNotNull(Transaction.getCurrent());
            Connection connection = transactionAwareDataSource.getConnection();

            return new TransactionTemplate(transactionManager).doInTransaction(() -> {
                Connection nestedTransactionTemplateConnection = transactionAwareDataSource.getConnection();
                assertEquals(connection, nestedTransactionTemplateConnection);
                return "nested";
            });
        });

        assertEquals("nested", result);
    }

    @Test
    void rolback() {
        RuntimeException re = new RuntimeException();

        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            transactionTemplate.doInTransaction(() -> {
                throw re;
            });
        });

        assertTrue(connection.isRollback());
        assertSame(re, thrownException);
    }

}