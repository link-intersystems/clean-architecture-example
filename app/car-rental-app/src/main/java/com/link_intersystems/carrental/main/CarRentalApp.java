package com.link_intersystems.carrental.main;

import com.link_intersystems.carrental.DefaultDomainEventBus;
import com.link_intersystems.carrental.DomainEventBus;
import com.link_intersystems.carrental.management.CarManagementViewConfig;
import com.link_intersystems.carrental.offer.CarOfferViewConfig;
import com.link_intersystems.carrental.swing.notification.DefaultMessageDialog;
import com.link_intersystems.carrental.ui.CarRentalMainFrame;
import com.link_intersystems.jdbc.JdbcTemplate;

public class CarRentalApp {

    private DataSourceConfig dataSourceConfig;
    private AOPConfig aopConfig;
    private DefaultMessageDialog messageDialog;

    public static void main(String[] args) {
        CarRentalApp carRentalApp = new CarRentalApp();
        carRentalApp.run(args);
    }

    void run(String[] args) {
        AppArgs appArgs = new AppArgs(args);
        dataSourceConfig = new DataSourceConfig(appArgs);
        TransactionConfig transactionConfig = new TransactionConfig(dataSourceConfig);
        aopConfig = new AOPConfig(transactionConfig);

        messageDialog = new DefaultMessageDialog();

        DefaultDomainEventBus defaultDomainEventBus = new DefaultDomainEventBus();

        CarManagementViewConfig carManagementViewConfig = createCarManagementViewConfig();
        defaultDomainEventBus.addSubscribers(carManagementViewConfig.getCarBookedEventSubscriber());

        CarOfferViewConfig carOfferViewConfig = createCarOfferViewConfig(defaultDomainEventBus);
        CarRentalConfig carRentalConfig = new CarRentalConfig(carOfferViewConfig, carManagementViewConfig, messageDialog);

        CarRentalMainFrame mainFrame = carRentalConfig.getMainFrame();

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> messageDialog.showException(e));
        messageDialog.setParentComponent(mainFrame.getComponent());

        openFrame(mainFrame);
    }

    private CarOfferViewConfig createCarOfferViewConfig(DomainEventBus domainEventBus) {
        JdbcTemplate carRentalJdbcTemplate = dataSourceConfig.getCarRentalJdbcTemplate();
        return new CarOfferViewConfig(carRentalJdbcTemplate, domainEventBus, aopConfig, messageDialog);
    }

    private CarManagementViewConfig createCarManagementViewConfig() {
        JdbcTemplate managementJdbcTemplate = dataSourceConfig.getManagementJdbcTemplate();
        return new CarManagementViewConfig(aopConfig, managementJdbcTemplate, messageDialog);
    }

    protected void openFrame(CarRentalMainFrame mainFrame) {
        mainFrame.show();
    }

}
