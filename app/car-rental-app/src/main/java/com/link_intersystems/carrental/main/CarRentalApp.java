package com.link_intersystems.carrental.main;

import com.link_intersystems.carrental.*;
import com.link_intersystems.carrental.management.CarManagementViewConfig;
import com.link_intersystems.carrental.offer.CarOfferViewConfig;
import com.link_intersystems.carrental.swing.notification.DefaultMessageDialog;
import com.link_intersystems.carrental.ui.CarRentalMainFrame;
import com.link_intersystems.jdbc.tx.TransactionListener;
import com.link_intersystems.jdbc.tx.TransactionManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CarRentalApp {

    private DataSourceConfig dataSourceConfig;
    private AOPConfig aopConfig;
    private DefaultMessageDialog messageDialog;
    private JpaBootstrap jpaBootstrap;

    public static void main(String[] args) {
        CarRentalApp carRentalApp = new CarRentalApp();
        carRentalApp.run(args);
    }

    void run(String[] args) {
        AppArgs appArgs = new AppArgs(args);
        dataSourceConfig = new DataSourceConfig(appArgs);
        JpaEntityManagerFactory jpaEntityManagerFactory = new JpaEntityManagerFactory();
        EntityManagerFactory entityManagerFactory = jpaEntityManagerFactory.getEntityManagerFactory(dataSourceConfig.getDataSource());
        TransactionConfig transactionConfig = new TransactionConfig(dataSourceConfig);
        TransactionManager transactionManager = transactionConfig.getTransactionManager();
        transactionManager.addTransactionListener(new TransactionListener() {
            @Override
            public void begin() {
                EntityManager entityManager = entityManagerFactory.createEntityManager();
                entityManager.getTransaction().begin();
                ThreadLoacalEntityManagerProxy.setEntityManager(entityManager);
            }

            @Override
            public void rollback() {
                EntityManager entityManager = ThreadLoacalEntityManagerProxy.getEntityManager();
                entityManager.getTransaction().rollback();
            }

            @Override
            public void beforeCommit() {
                EntityManager entityManager = ThreadLoacalEntityManagerProxy.getEntityManager();
                EntityTransaction transaction = entityManager.getTransaction();
                transaction.commit();

                ThreadLoacalEntityManagerProxy.setEntityManager(null);
            }
        });

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
        EntityManager entityManager = ThreadLoacalEntityManagerProxy.createEntityManager();
        return new CarOfferViewConfig(entityManager, eventPublisher, aopConfig, messageDialog);
    }

    private CarManagementViewConfig createCarManagementViewConfig() {
        return new CarManagementViewConfig(aopConfig, dataSourceConfig.getDataSource(), messageDialog);
    }

    protected void openFrame(CarRentalMainFrame mainFrame) {
        mainFrame.show();
    }

}
