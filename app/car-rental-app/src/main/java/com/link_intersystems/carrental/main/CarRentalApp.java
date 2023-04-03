package com.link_intersystems.carrental.main;

import com.link_intersystems.carrental.*;
import com.link_intersystems.carrental.management.CarManagementViewConfig;
import com.link_intersystems.carrental.offer.CarOfferViewConfig;
import com.link_intersystems.carrental.swing.notification.DefaultMessageDialog;
import com.link_intersystems.carrental.ui.CarRentalMainFrame;
import com.link_intersystems.jdbc.JdbcTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

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

        DomainEventBus domainEventBus = createDomainEventBus(appArgs);

        CarManagementViewConfig carManagementViewConfig = createCarManagementViewConfig();
        domainEventBus.addSubscribers(carManagementViewConfig.getCarBookedEventSubscriber());

        CarOfferViewConfig carOfferViewConfig = createCarOfferViewConfig(domainEventBus);
        CarRentalConfig carRentalConfig = new CarRentalConfig(carOfferViewConfig, carManagementViewConfig, messageDialog);

        CarRentalMainFrame mainFrame = carRentalConfig.getMainFrame();

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> messageDialog.showException(e));
        messageDialog.setParentComponent(mainFrame.getComponent());

        openFrame(mainFrame);
    }

    private DomainEventBus createDomainEventBus(AppArgs appArgs) {
        Map<String, Supplier<EventDispatchStrategy>> strategyMap = new HashMap<>();
        strategyMap.put("sync", SyncEventDispatchStrategy::new);
        strategyMap.put("async", AsyncEventDispatchStrategy::new);

        String eventBusType = appArgs.getArg("eb", "sync");
        EventDispatchStrategy eventDispatchStrategy = strategyMap.getOrDefault(eventBusType, SyncEventDispatchStrategy::new).get();
        return new DomainEventBus(eventDispatchStrategy);
    }

    private CarOfferViewConfig createCarOfferViewConfig(DomainEventPublisher eventPublisher) {
        JdbcTemplate carRentalJdbcTemplate = dataSourceConfig.getCarRentalJdbcTemplate();
        return new CarOfferViewConfig(carRentalJdbcTemplate, eventPublisher, aopConfig, messageDialog);
    }

    private CarManagementViewConfig createCarManagementViewConfig() {
        JdbcTemplate managementJdbcTemplate = dataSourceConfig.getManagementJdbcTemplate();
        return new CarManagementViewConfig(aopConfig, managementJdbcTemplate, messageDialog);
    }

    protected void openFrame(CarRentalMainFrame mainFrame) {
        mainFrame.show();
    }

}
