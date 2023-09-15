package com.link_intersystems.carrental.components.jpa;

import com.link_intersystems.aop.MethodInterceptor;
import com.link_intersystems.carrental.DomainEventBus;
import com.link_intersystems.carrental.booking.CarBookingComponent;
import com.link_intersystems.carrental.booking.CarBookingJpaConfig;
import com.link_intersystems.carrental.booking.JpaCarBookingComponent;
import com.link_intersystems.carrental.components.AOPConfig;
import com.link_intersystems.carrental.components.ComponentsConfig;
import com.link_intersystems.carrental.management.CarManagementJpaConfig;
import com.link_intersystems.carrental.management.booking.JpaManagementCarBookingComponent;
import com.link_intersystems.carrental.management.booking.ManagementCarBookingComponent;
import com.link_intersystems.carrental.management.rental.JpaManagementRentalComponent;
import com.link_intersystems.carrental.management.rental.ManagementRentalComponent;
import com.link_intersystems.tx.CompositeTransactionManager;
import com.link_intersystems.tx.TransactionManager;
import com.link_intersystems.tx.TransactionMethodInterceptor;
import jakarta.persistence.EntityManager;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class JpaComponentsConfig implements ComponentsConfig {

    private final DomainEventBus domainEventBus;
    private AOPConfig aopConfig;
    private DataSourceConfig dataSourceConfig;
    private EntityManager bookingEmProxy = ThreadLoacalEntityManagerProxy.createProxy("BOOKING");
    private EntityManager managementEmProxy = ThreadLoacalEntityManagerProxy.createProxy("MANAGEMENT");

    public JpaComponentsConfig(Properties properties, DomainEventBus domainEventBus) {
        this.dataSourceConfig = new DataSourceConfig(properties);

        this.domainEventBus = domainEventBus;

        CarBookingJpaConfig carBookingJpaConfig = new CarBookingJpaConfig(dataSourceConfig.getDataSource());
        TransactionManager bookingTm = new JpaLocalTransactionManager(carBookingJpaConfig.getEntityManagerFactory());
        CarManagementJpaConfig carManagementJpaConfig = new CarManagementJpaConfig(dataSourceConfig.getDataSource());
        TransactionManager managementTm = new JpaLocalTransactionManager(carManagementJpaConfig.getEntityManagerFactory());

        TransactionManager compositeTransactionManager = new CompositeTransactionManager(Arrays.asList(bookingTm, managementTm));
        TransactionMethodInterceptor managementTmInterceptor = new TransactionMethodInterceptor(compositeTransactionManager);
        List<MethodInterceptor> methodInterceptors = Arrays.asList(managementTmInterceptor);

        this.aopConfig = new AOPConfig(methodInterceptors);
    }

    @Override
    public CarBookingComponent getCarBookingComponent() {
        return new JpaCarBookingComponent(aopConfig, bookingEmProxy);
    }

    @Override
    public ManagementRentalComponent getManagementRentalComponent() {
        return new JpaManagementRentalComponent(aopConfig, managementEmProxy, domainEventBus);
    }

    @Override
    public ManagementCarBookingComponent getManagementCarBookingComponent() {
        return new JpaManagementCarBookingComponent(aopConfig, managementEmProxy);
    }

}
