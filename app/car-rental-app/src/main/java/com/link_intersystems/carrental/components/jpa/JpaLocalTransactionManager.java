package com.link_intersystems.carrental.components.jpa;

import com.link_intersystems.tx.Transaction;
import com.link_intersystems.tx.TransactionManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceUnitUtil;

public class JpaLocalTransactionManager implements TransactionManager {

    class JpaLocalTransaction extends Transaction {

        private final EntityManager entityManager;

        public JpaLocalTransaction(EntityManager entityManager) {
            this.entityManager = entityManager;
        }

        @Override
        protected void commit() {
            try {
                EntityTransaction transaction = entityManager.getTransaction();
                transaction.commit();
            } finally {
                try {
                    entityManager.close();
                } finally {
                    ThreadLoacalEntityManagerProxy.removeEntityManager(getPuName());
                }
            }
        }

        @Override
        protected void rollback() {
            try {
                entityManager.getTransaction().rollback();
            } finally {
                try {
                    entityManager.close();
                } finally {
                    ThreadLoacalEntityManagerProxy.removeEntityManager(getPuName());
                }
            }
        }
    }

    private EntityManagerFactory entityManagerFactory;

    public JpaLocalTransactionManager(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Transaction beginTransaction() throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        ThreadLoacalEntityManagerProxy.setEntityManager(getPuName(), entityManager);
        return new JpaLocalTransaction(entityManager);
    }

    private String getPuName() {
        return (String) this.entityManagerFactory.getProperties().get("hibernate.persistenceUnitName");
    }
}
