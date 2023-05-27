package com.link_intersystems.carrental.main;

import com.link_intersystems.carrental.*;
import com.link_intersystems.carrental.login.LoginViewConfig;
import com.link_intersystems.carrental.management.CarManagementViewConfig;
import com.link_intersystems.carrental.offer.CarOfferViewConfig;
import com.link_intersystems.carrental.swing.notification.DefaultMessageDialog;
import com.link_intersystems.jdbc.JdbcTemplate;
import com.link_intersystems.swing.action.ActionTrigger;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class BootstrapController {

    private ActionTrigger actionTrigger = new ActionTrigger(this);

    private DataSourceConfig dataSourceConfig;
    private AOPConfig aopConfig;
    private DefaultMessageDialog messageDialog;

    public void execute(AppArgs appArgs) {
        dataSourceConfig = new DataSourceConfig(appArgs);
        TransactionConfig transactionConfig = new TransactionConfig(dataSourceConfig);
        aopConfig = new AOPConfig(transactionConfig);
        messageDialog = new DefaultMessageDialog();

        DomainEventBus domainEventBus = createDomainEventBus(appArgs);

        CarManagementViewConfig carManagementViewConfig = createCarManagementViewConfig();
        domainEventBus.addSubscribers(carManagementViewConfig.getCarBookedEventSubscriber());

        CarOfferViewConfig carOfferViewConfig = createCarOfferViewConfig(domainEventBus);


        MainConfig mainConfig = new MainConfig(carOfferViewConfig, carManagementViewConfig);

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> messageDialog.showException(e));
//        messageDialog.setParentComponent(mainFrame.getComponent());


        LoginViewConfig loginViewConfig = new LoginViewConfig(dataSourceConfig.getCarRentalJdbcTemplate(), mainConfig::getMainFrame);

        Action initiateLoginAction = loginViewConfig.getInitiateLoginAction();
        actionTrigger.performAction(initiateLoginAction);
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

}
