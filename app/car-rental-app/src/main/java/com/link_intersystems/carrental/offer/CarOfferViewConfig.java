package com.link_intersystems.carrental.offer;

import com.link_intersystems.carrental.DomainEventPublisher;
import com.link_intersystems.carrental.booking.CarBookingComponent;
import com.link_intersystems.carrental.booking.CarBookingUseCase;
import com.link_intersystems.carrental.booking.CarOfferUseCase;
import com.link_intersystems.carrental.components.ComponentsConfig;
import com.link_intersystems.carrental.offer.ui.CarOfferUIConfig;
import com.link_intersystems.carrental.offer.ui.CarOfferView;
import com.link_intersystems.carrental.swing.notification.MessageDialog;

import static java.util.Objects.*;

public class CarOfferViewConfig {

    private DomainEventPublisher eventPublisher;
    private ComponentsConfig componentsConfig;
    private MessageDialog messageDialog;

    public CarOfferViewConfig(ComponentsConfig componentsConfig, DomainEventPublisher eventPublisher, MessageDialog messageDialog) {
        this.eventPublisher = requireNonNull(eventPublisher);
        this.componentsConfig = requireNonNull(componentsConfig);
        this.messageDialog = requireNonNull(messageDialog);
    }

    public CarOfferView getCarOfferView() {
        CarBookingComponent carBookingComponent = componentsConfig.getCarBookingComponent();
        CarBookingUseCase carBookingUseCase = carBookingComponent.getCarBookingUseCase(eventPublisher);
        CarOfferUseCase carOfferUseCase = carBookingComponent.getCarOfferUseCase();

        CarOfferUIConfig carOfferUIConfig = new CarOfferUIConfig(messageDialog);
        return carOfferUIConfig.getCarOfferView(carBookingUseCase, carOfferUseCase, messageDialog);
    }

}
