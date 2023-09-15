package com.link_intersystems.carrental.main;

import com.link_intersystems.carrental.*;
import com.link_intersystems.carrental.components.ComponentsConfig;
import com.link_intersystems.carrental.components.ComponentsConfigProvider;
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

        DefaultMessageDialog messageDialog = new DefaultMessageDialog();

        DomainEventBus domainEventBus = createDomainEventBus(appProperties);

        ComponentsConfig componentsConfig = ComponentsConfigProvider.findComponentsConfig(appProperties, domainEventBus);
        CarManagementViewConfig carManagementViewConfig = createCarManagementViewConfig(componentsConfig, messageDialog, domainEventBus);

        CarOfferViewConfig carOfferViewConfig = createCarOfferViewConfig(componentsConfig, domainEventBus, messageDialog);
        CarRentalConfig carRentalConfig = new CarRentalConfig(carOfferViewConfig, carManagementViewConfig, messageDialog);

        CarRentalMainFrame mainFrame = carRentalConfig.getMainFrame();

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> messageDialog.showException(e));
        messageDialog.setParentComponent(mainFrame.getComponent());

        openFrame(mainFrame);
    }

    private DomainEventBus createDomainEventBus(Properties appProperties) {
        Map<String, Supplier<EventDispatchStrategy>> strategyMap = new HashMap<>();
        strategyMap.put("sync", SyncEventDispatchStrategy::new);
        strategyMap.put("async", () -> new AsyncEventDispatchStrategy(Integer.parseInt(appProperties.getProperty("asyncDelay", "0"))));

        String eventBusType = appProperties.getProperty("eb", "sync");
        EventDispatchStrategy eventDispatchStrategy = strategyMap.getOrDefault(eventBusType, SyncEventDispatchStrategy::new).get();
        return new DomainEventBus(eventDispatchStrategy);
    }

    private CarOfferViewConfig createCarOfferViewConfig(ComponentsConfig componentsConfig, DomainEventPublisher eventPublisher, DefaultMessageDialog messageDialog) {
        return new CarOfferViewConfig(componentsConfig, eventPublisher, messageDialog);
    }

    private CarManagementViewConfig createCarManagementViewConfig(ComponentsConfig componentsConfig, DefaultMessageDialog messageDialog, DomainEventBus domainEventBus) {
        return new CarManagementViewConfig(componentsConfig, domainEventBus, messageDialog);
    }

    protected void openFrame(CarRentalMainFrame mainFrame) {
        mainFrame.show();
    }

}
