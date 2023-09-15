package com.link_intersystems.carrental.components.jdbc;

import com.link_intersystems.aop.MethodInterceptor;
import com.link_intersystems.carrental.DomainEventBus;
import com.link_intersystems.carrental.booking.CarBookingComponent;
import com.link_intersystems.carrental.booking.JdbcBookingComponent;
import com.link_intersystems.carrental.components.AOPConfig;
import com.link_intersystems.carrental.components.ComponentsConfig;
import com.link_intersystems.carrental.management.booking.JdbcManagementCarBookingComponent;
import com.link_intersystems.carrental.management.booking.ManagementCarBookingComponent;
import com.link_intersystems.carrental.management.rental.JdbcManagementRentalComponent;
import com.link_intersystems.carrental.management.rental.ManagementRentalComponent;
import com.link_intersystems.tx.TransactionMethodInterceptor;
import com.link_intersystems.tx.jdbc.JdbcLocalTransactionManager;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class JdbcComponentsConfig implements ComponentsConfig {

    private final DomainEventBus domainEventBus;
    private AOPConfig aopConfig;
    private DataSourceConfig dataSourceConfig;

    public JdbcComponentsConfig(Properties properties, DomainEventBus domainEventBus) {
        this.dataSourceConfig = new DataSourceConfig(properties);
        this.domainEventBus = domainEventBus;
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
    public ManagementRentalComponent getManagementRentalComponent() {
        return new JdbcManagementRentalComponent(getAopConfig(), getDataSourceConfig().getManagementJdbcTemplate(), domainEventBus);
    }

    @Override
    public ManagementCarBookingComponent getManagementCarBookingComponent() {
        return new JdbcManagementCarBookingComponent(getAopConfig(), getDataSourceConfig().getManagementJdbcTemplate());
    }


}
