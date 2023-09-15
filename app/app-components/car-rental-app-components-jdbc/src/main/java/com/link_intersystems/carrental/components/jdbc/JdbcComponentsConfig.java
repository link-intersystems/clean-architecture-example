package com.link_intersystems.carrental.components.jdbc;

import com.link_intersystems.aop.MethodInterceptor;
import com.link_intersystems.carrental.DomainEventPublisher;
import com.link_intersystems.carrental.booking.CarBookingComponent;
import com.link_intersystems.carrental.booking.JdbcBookingComponent;
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
import com.link_intersystems.tx.TransactionMethodInterceptor;
import com.link_intersystems.tx.jdbc.JdbcLocalTransactionManager;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class JdbcComponentsConfig implements ComponentsConfig {

    private AOPConfig aopConfig;
    private DataSourceConfig dataSourceConfig;

    public JdbcComponentsConfig(Properties properties) {
        this.dataSourceConfig = new DataSourceConfig(properties);
    }

    public AOPConfig getAopConfig() {
        if (aopConfig == null) {
            aopConfig = initAopConfig();
        }
        return aopConfig;
    }

    protected AOPConfig initAopConfig() {
        DataSourceConfig dataSourceConfig = getDataSourceConfig();
        DataSource dataSource = dataSourceConfig.getDataSource();
        JdbcLocalTransactionManager transactionManager = new JdbcLocalTransactionManager(dataSource);
        TransactionMethodInterceptor transactionMethodInterceptor = new TransactionMethodInterceptor(transactionManager);

        List<MethodInterceptor> methodInterceptors = Arrays.asList(transactionMethodInterceptor);
        return new AOPConfig(methodInterceptors);
    }

    protected DataSourceConfig getDataSourceConfig() {
        return dataSourceConfig;
    }

    @Override
    public CarBookingComponent getCarBookingComponent() {
        return new JdbcBookingComponent(getAopConfig(), getDataSourceConfig().getBookingJdbcTemplate());
    }

    @Override
    public ListCarBookingComponent getListCarBookingComponent() {
        return new JdbcListCarBookingComponent(getAopConfig(), getDataSourceConfig().getManagementJdbcTemplate());
    }

    @Override
    public PickupCarComponent getPickupCarComponent(DomainEventPublisher eventPublisher) {
        return new JdbcPickupCarComponent(getAopConfig(), getDataSourceConfig().getManagementJdbcTemplate(), eventPublisher);
    }

    @Override
    public ListPickupCarComponent getListPickupCarComponent() {
        return new JdbcListPickupCarComponent(getAopConfig(), getDataSourceConfig().getManagementJdbcTemplate());
    }

    @Override
    public ReturnCarComponent getReturnCarComponent() {
        return new JdbcReturnCarComponent(getAopConfig(), getDataSourceConfig().getManagementJdbcTemplate());
    }

    @Override
    public GetPickupCarComponent getGetPickupCarComponent() {
        return new JdbcGetPickupCarComponent(getAopConfig(), getDataSourceConfig().getManagementJdbcTemplate());
    }

    @Override
    public CreateCarBookingComponent getCreateCarBookingComponent() {
        return new JdbcCreateCarBookingComponent(getAopConfig(), getDataSourceConfig().getManagementJdbcTemplate());
    }


}
