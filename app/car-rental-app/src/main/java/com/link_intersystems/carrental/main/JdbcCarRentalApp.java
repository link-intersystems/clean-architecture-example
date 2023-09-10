package com.link_intersystems.carrental.main;

public class JdbcCarRentalApp extends AbstractCarRentalApp {

    public static void main(String[] args) {
        JdbcCarRentalApp carRentalApp = new JdbcCarRentalApp();
        carRentalApp.run(args);
    }

    protected ComponentsConfig getComponentsConfig(AppArgs appArgs) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig(appArgs);
        TransactionConfig transactionConfig = new TransactionConfig(dataSourceConfig);
        AOPConfig aopConfig = new AOPConfig(transactionConfig);
        return new JdbcComponentsConfig(aopConfig, dataSourceConfig);
    }

}
