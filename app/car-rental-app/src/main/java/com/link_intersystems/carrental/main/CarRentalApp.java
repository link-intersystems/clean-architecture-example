package com.link_intersystems.carrental.main;

import com.link_intersystems.carrental.*;
import com.link_intersystems.carrental.components.ComponentsConfig;
import com.link_intersystems.carrental.components.jdbc.JdbcComponentsConfig;
import com.link_intersystems.carrental.components.jpa.JpaComponentsConfig;
import com.link_intersystems.carrental.management.CarManagementViewConfig;
import com.link_intersystems.carrental.offer.CarOfferViewConfig;
import com.link_intersystems.carrental.swing.notification.DefaultMessageDialog;
import com.link_intersystems.carrental.ui.CarRentalMainFrame;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;

public class CarRentalApp {


    public static void main(String[] args) {
        CarRentalApp carRentalApp = new CarRentalApp();
        carRentalApp.run(args);
    }

    public void run(String[] args) {
        AppArgsParser appArgsParser = new AppArgsParser();
        Properties appProperties = appArgsParser.parse(args);
        ComponentsConfig componentsConfig = getComponentsConfig(appProperties);

        DefaultMessageDialog messageDialog = new DefaultMessageDialog();

        DomainEventBus domainEventBus = createDomainEventBus(appProperties);

        CarManagementViewConfig carManagementViewConfig = createCarManagementViewConfig(componentsConfig, messageDialog);
        domainEventBus.addSubscribers(carManagementViewConfig.getCarBookedEventSubscriber());

        CarOfferViewConfig carOfferViewConfig = createCarOfferViewConfig(componentsConfig, domainEventBus, messageDialog);
        CarRentalConfig carRentalConfig = new CarRentalConfig(carOfferViewConfig, carManagementViewConfig, messageDialog);

        CarRentalMainFrame mainFrame = carRentalConfig.getMainFrame();

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> messageDialog.showException(e));
        messageDialog.setParentComponent(mainFrame.getComponent());

        openFrame(mainFrame);
    }

    protected ComponentsConfig getComponentsConfig(Properties appProperties) {

        String persistenceProvider = appProperties.getProperty("persistence-provider", "jdbc");
        if ("jdbc".equals(persistenceProvider)) {
            return new JdbcComponentsConfig(appProperties);
        } else if ("jpa".equals(persistenceProvider)) {
            return new JpaComponentsConfig(appProperties);
        } else {
            throw new IllegalArgumentException("Unsupported persistence-provider '" + persistenceProvider + "'");
        }
    }

    private DomainEventBus createDomainEventBus(Properties appProperties) {
        Map<String, Supplier<EventDispatchStrategy>> strategyMap = new HashMap<>();
        strategyMap.put("sync", SyncEventDispatchStrategy::new);
        strategyMap.put("async", AsyncEventDispatchStrategy::new);

        String eventBusType = appProperties.getProperty("eb", "sync");
        EventDispatchStrategy eventDispatchStrategy = strategyMap.getOrDefault(eventBusType, SyncEventDispatchStrategy::new).get();
        return new DomainEventBus(eventDispatchStrategy);
    }

    private CarOfferViewConfig createCarOfferViewConfig(ComponentsConfig componentsConfig, DomainEventPublisher eventPublisher, DefaultMessageDialog messageDialog) {
        return new CarOfferViewConfig(componentsConfig, eventPublisher, messageDialog);
    }

    private CarManagementViewConfig createCarManagementViewConfig(ComponentsConfig componentsConfig, DefaultMessageDialog messageDialog) {
        return new CarManagementViewConfig(componentsConfig, messageDialog);
    }

    protected void openFrame(CarRentalMainFrame mainFrame) {
        mainFrame.show();
    }

}
