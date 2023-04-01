package com.link_intersystems.jdbc.tx;

import com.link_intersystems.jdbc.ConnectionSupplierDataSourceAdapter;
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
        transactionManager = new LocalTransactionManager(() -> connection);
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

    @Test
    void transactionProxy() {
        ServiceImpl service = new ServiceImpl();
        Service serviceProxy = TransactionProxy.create(service, transactionManager);

        serviceProxy.execute();

        assertEquals(1, service.invocationCount);
    }

    private static interface Service {
        public void execute();
    }

    private static class ServiceImpl implements Service {

        private int invocationCount;

        @Override
        public void execute() {
            invocationCount++;
        }

    }
}