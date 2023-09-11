package com.link_intersystems.carrental.components.jpa;

import com.link_intersystems.aop.MethodInterceptor;
import com.link_intersystems.carrental.components.AOPConfig;
import com.link_intersystems.carrental.components.jdbc.DataSourceConfig;
import com.link_intersystems.carrental.components.jdbc.JdbcComponentsConfig;
import com.link_intersystems.carrental.offer.CarOfferComponent;
import com.link_intersystems.carrental.offer.JpaCarOfferComponent;
import com.link_intersystems.tx.TransactionManager;
import com.link_intersystems.tx.TransactionMethodInterceptor;
import com.link_intersystems.tx.jdbc.JdbcLocalTransactionManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class JpaComponentsConfig extends JdbcComponentsConfig {

    private AOPConfig aopConfig;
    private DataSourceConfig dataSourceConfig;
    private EntityManager bookingEmProxy = ThreadLoacalEntityManagerProxy.createProxy("BOOKING");

    public JpaComponentsConfig(Properties properties) {
        super(properties);
        this.dataSourceConfig = new DataSourceConfig(properties);

        JdbcLocalTransactionManager transactionManager = new JdbcLocalTransactionManager(dataSourceConfig.getDataSource());
        TransactionMethodInterceptor transactionMethodInterceptor = new TransactionMethodInterceptor(transactionManager);

        List<MethodInterceptor> methodInterceptors = Arrays.asList(transactionMethodInterceptor);
        this.aopConfig = new AOPConfig(methodInterceptors);
    }

    private JpaConfig jpaConfig;

    public JpaConfig getJpaConfig() {
        if (jpaConfig == null) {
            jpaConfig = new JpaConfig(dataSourceConfig.getDataSource());
        }
        return jpaConfig;
    }

    @Override
    protected AOPConfig initAopConfig() {
        JpaConfig jpaConfig = getJpaConfig();
        EntityManagerFactory bookingEmf = jpaConfig.getEntityManagerFactory("BOOKING");
        TransactionManager bookingTransactionManager = new JpaLocalTransactionManager(bookingEmf);
        TransactionMethodInterceptor bookingTmInterceptor = new TransactionMethodInterceptor(bookingTransactionManager);
        List<MethodInterceptor> methodInterceptors = Arrays.asList(bookingTmInterceptor);
        return new AOPConfig(methodInterceptors);
    }

    @Override
    public CarOfferComponent getCarOfferComponent() {
        return new JpaCarOfferComponent(getAopConfig(), bookingEmProxy);
    }

}
