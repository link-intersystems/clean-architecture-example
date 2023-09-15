package com.link_intersystems.carrental.components.jpa;

import com.link_intersystems.aop.MethodInterceptor;
import com.link_intersystems.carrental.booking.CarBookingComponent;
import com.link_intersystems.carrental.booking.CarBookingJpaConfig;
import com.link_intersystems.carrental.booking.JpaCarBookingComponent;
import com.link_intersystems.carrental.components.AOPConfig;
import com.link_intersystems.carrental.components.ComponentsConfig;
import com.link_intersystems.carrental.management.CarManagementJpaConfig;
import com.link_intersystems.carrental.management.booking.create.CreateCarBookingComponent;
import com.link_intersystems.carrental.management.booking.create.JpaCreateCarBookingComponent;
import com.link_intersystems.carrental.management.booking.list.JpaListCarBookingComponent;
import com.link_intersystems.carrental.management.booking.list.ListCarBookingComponent;
import com.link_intersystems.carrental.management.rental.pickup.JpaPickupCarComponent;
import com.link_intersystems.carrental.management.rental.pickup.PickupCarComponent;
import com.link_intersystems.carrental.management.rental.pickup.get.GetPickupCarComponent;
import com.link_intersystems.carrental.management.rental.pickup.get.JpaGetPickupCarComponent;
import com.link_intersystems.carrental.management.rental.pickup.list.JpaListPickupCarComponent;
import com.link_intersystems.carrental.management.rental.pickup.list.ListPickupCarComponent;
import com.link_intersystems.carrental.management.rental.returnCar.JpaReturnCarComponent;
import com.link_intersystems.carrental.management.rental.returnCar.ReturnCarComponent;
import com.link_intersystems.carrental.offer.CarOfferComponent;
import com.link_intersystems.carrental.offer.JpaCarOfferComponent;
import com.link_intersystems.tx.CompositeTransactionManager;
import com.link_intersystems.tx.TransactionManager;
import com.link_intersystems.tx.TransactionMethodInterceptor;
import jakarta.persistence.EntityManager;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class JpaComponentsConfig implements ComponentsConfig {

    private AOPConfig aopConfig;
    private DataSourceConfig dataSourceConfig;
    private EntityManager bookingEmProxy = ThreadLoacalEntityManagerProxy.createProxy("BOOKING");
    private EntityManager managementEmProxy = ThreadLoacalEntityManagerProxy.createProxy("MANAGEMENT");

    public JpaComponentsConfig(Properties properties) {
        this.dataSourceConfig = new DataSourceConfig(properties);

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
    public CarOfferComponent getCarOfferComponent() {
        return new JpaCarOfferComponent(aopConfig, bookingEmProxy);
    }

    protected DataSourceConfig getDataSourceConfig() {
        return dataSourceConfig;
    }

    @Override
    public CarBookingComponent getCarBookingComponent() {
        return new JpaCarBookingComponent(aopConfig, bookingEmProxy);
    }

    @Override
    public ListCarBookingComponent getListCarBookingComponent() {
        return new JpaListCarBookingComponent(aopConfig, managementEmProxy);
    }

    @Override
    public PickupCarComponent getPickupCarComponent() {
        return new JpaPickupCarComponent(aopConfig, managementEmProxy);
    }

    @Override
    public ListPickupCarComponent getListPickupCarComponent() {
        return new JpaListPickupCarComponent(aopConfig, managementEmProxy);
    }

    @Override
    public ReturnCarComponent getReturnCarComponent() {
        return new JpaReturnCarComponent(aopConfig, managementEmProxy);
    }

    @Override
    public GetPickupCarComponent getGetPickupCarComponent() {
        return new JpaGetPickupCarComponent(aopConfig, managementEmProxy);
    }

    @Override
    public CreateCarBookingComponent getCreateCarBookingComponent() {
        return new JpaCreateCarBookingComponent(aopConfig, managementEmProxy);
    }

}
