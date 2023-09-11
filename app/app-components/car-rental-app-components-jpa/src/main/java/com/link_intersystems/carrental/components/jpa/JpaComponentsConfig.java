package com.link_intersystems.carrental.components.jpa;

import com.link_intersystems.aop.MethodInterceptor;
import com.link_intersystems.carrental.booking.CarBookingComponent;
import com.link_intersystems.carrental.booking.JpaCarBookingComponent;
import com.link_intersystems.carrental.components.AOPConfig;
import com.link_intersystems.carrental.components.ComponentsConfig;
import com.link_intersystems.carrental.management.booking.create.CreateCarBookingComponent;
import com.link_intersystems.carrental.management.booking.create.JdbcCreateCarBookingComponent;
import com.link_intersystems.carrental.management.booking.list.JdbcListCarBookingComponent;
import com.link_intersystems.carrental.management.booking.list.ListCarBookingComponent;
import com.link_intersystems.carrental.management.rental.pickup.JdbcPickupCarComponent;
import com.link_intersystems.carrental.management.rental.pickup.PickupCarComponent;
import com.link_intersystems.carrental.management.rental.pickup.get.GetPickupCarComponent;
import com.link_intersystems.carrental.management.rental.pickup.get.JdbcGetPickupCarComponent;
import com.link_intersystems.carrental.management.rental.pickup.list.JdbcListPickupCarComponent;
import com.link_intersystems.carrental.management.rental.pickup.list.ListPickupCarComponent;
import com.link_intersystems.carrental.management.rental.returnCar.JdbcReturnCarComponent;
import com.link_intersystems.carrental.management.rental.returnCar.ReturnCarComponent;
import com.link_intersystems.carrental.offer.CarOfferComponent;
import com.link_intersystems.carrental.offer.JpaCarOfferComponent;
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

    public JpaComponentsConfig(Properties properties) {
        this.dataSourceConfig = new DataSourceConfig(properties);
        JpaConfig jpaConfig = new JpaConfig(dataSourceConfig.getDataSource());

        TransactionManager transactionManager = new JpaLocalTransactionManager(jpaConfig.getEntityManagerFactory("BOOKING"));
        TransactionMethodInterceptor transactionMethodInterceptor = new TransactionMethodInterceptor(transactionManager);

        List<MethodInterceptor> methodInterceptors = Arrays.asList(transactionMethodInterceptor);
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
        return new JdbcListCarBookingComponent(aopConfig, getDataSourceConfig().getManagementJdbcTemplate());
    }

    @Override
    public PickupCarComponent getPickupCarComponent() {
        return new JdbcPickupCarComponent(aopConfig, getDataSourceConfig().getManagementJdbcTemplate());
    }

    @Override
    public ListPickupCarComponent getListPickupCarComponent() {
        return new JdbcListPickupCarComponent(aopConfig, getDataSourceConfig().getManagementJdbcTemplate());
    }

    @Override
    public ReturnCarComponent getReturnCarComponent() {
        return new JdbcReturnCarComponent(aopConfig, getDataSourceConfig().getManagementJdbcTemplate());
    }

    @Override
    public GetPickupCarComponent getGetPickupCarComponent() {
        return new JdbcGetPickupCarComponent(aopConfig, getDataSourceConfig().getManagementJdbcTemplate());
    }

    @Override
    public CreateCarBookingComponent getCreateCarBookingComponent() {
        return new JdbcCreateCarBookingComponent(aopConfig, getDataSourceConfig().getManagementJdbcTemplate());
    }

}
