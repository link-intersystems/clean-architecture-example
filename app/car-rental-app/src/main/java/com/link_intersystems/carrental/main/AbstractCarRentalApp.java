package com.link_intersystems.carrental.main;

import com.link_intersystems.carrental.*;
import com.link_intersystems.carrental.management.CarManagementViewConfig;
import com.link_intersystems.carrental.offer.CarOfferViewConfig;
import com.link_intersystems.carrental.swing.notification.DefaultMessageDialog;
import com.link_intersystems.carrental.ui.CarRentalMainFrame;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class AbstractCarRentalApp {


    public void run(String[] args) {
        AppArgs appArgs = new AppArgs(args);
        ComponentsConfig componentsConfig = getComponentsConfig(appArgs);

        DefaultMessageDialog messageDialog = new DefaultMessageDialog();

        DomainEventBus domainEventBus = createDomainEventBus(appArgs);

        CarManagementViewConfig carManagementViewConfig = createCarManagementViewConfig(componentsConfig, messageDialog);
        domainEventBus.addSubscribers(carManagementViewConfig.getCarBookedEventSubscriber());

        CarOfferViewConfig carOfferViewConfig = createCarOfferViewConfig(componentsConfig, domainEventBus, messageDialog);
        CarRentalConfig carRentalConfig = new CarRentalConfig(carOfferViewConfig, carManagementViewConfig, messageDialog);

        CarRentalMainFrame mainFrame = carRentalConfig.getMainFrame();

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> messageDialog.showException(e));
        messageDialog.setParentComponent(mainFrame.getComponent());

        openFrame(mainFrame);
    }

    protected abstract ComponentsConfig getComponentsConfig(AppArgs appArgs);

    private DomainEventBus createDomainEventBus(AppArgs appArgs) {
        Map<String, Supplier<EventDispatchStrategy>> strategyMap = new HashMap<>();
        strategyMap.put("sync", SyncEventDispatchStrategy::new);
        strategyMap.put("async", AsyncEventDispatchStrategy::new);

        String eventBusType = appArgs.getArg("eb", "sync");
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
