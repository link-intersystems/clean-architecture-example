package com.link_intersystems.carrental.offer;

import com.link_intersystems.carrental.DomainEventPublisher;
import com.link_intersystems.carrental.booking.CarBookingComponent;
import com.link_intersystems.carrental.booking.CarBookingUseCase;
import com.link_intersystems.carrental.main.AOPConfig;
import com.link_intersystems.carrental.offer.ui.CarOfferUIConfig;
import com.link_intersystems.carrental.offer.ui.CarOfferView;
import com.link_intersystems.carrental.swing.notification.MessageDialog;
import jakarta.persistence.EntityManager;

import static java.util.Objects.*;

public class CarOfferViewConfig {

    private DomainEventPublisher eventPublisher;
    private AOPConfig aopConfig;
    private MessageDialog messageDialog;
    private EntityManager entityManager;

    public CarOfferViewConfig(EntityManager entityManager, DomainEventPublisher eventPublisher, AOPConfig aopConfig, MessageDialog messageDialog) {
        this.entityManager = requireNonNull(entityManager);
        this.eventPublisher = requireNonNull(eventPublisher);
        this.aopConfig = requireNonNull(aopConfig);
        this.messageDialog = requireNonNull(messageDialog);
    }

    public CarOfferView getCarOfferView() {
        CarBookingUseCase carBookingUseCase = createCarBookingUseCase();
        CarOfferUseCase carOfferUseCase = createCarOfferUseCase();

        CarOfferUIConfig carOfferUIConfig = new CarOfferUIConfig(messageDialog);
        return carOfferUIConfig.getCarOfferView(carBookingUseCase, carOfferUseCase, messageDialog);
    }

    private CarOfferUseCase createCarOfferUseCase() {
        CarOfferComponent carOfferComponent = new CarOfferComponent(entityManager);
        CarOfferUseCase carOfferUseCase = carOfferComponent.createCarOfferUseCase();
        return aopConfig.applyAOP(CarOfferUseCase.class, carOfferUseCase);
    }

    private CarBookingUseCase createCarBookingUseCase() {
        CarBookingComponent carBookingComponent = new CarBookingComponent(entityManager);
        CarBookingUseCase carBookingUseCase = carBookingComponent.getCarBookingUseCase(eventPublisher);
        return aopConfig.applyAOP(CarBookingUseCase.class, carBookingUseCase);
    }
}
