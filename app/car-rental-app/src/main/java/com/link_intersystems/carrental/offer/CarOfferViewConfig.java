package com.link_intersystems.carrental.offer;

import com.link_intersystems.carrental.AOPConfig;
import com.link_intersystems.carrental.DomainEventSubscriber;
import com.link_intersystems.carrental.booking.CarBookingComponent;
import com.link_intersystems.carrental.booking.CarBookingUseCase;
import com.link_intersystems.carrental.swing.notification.MessageDialog;
import com.link_intersystems.jdbc.JdbcTemplate;

import java.util.List;

import static java.util.Objects.*;

public class CarOfferViewConfig {

    private JdbcTemplate jdbcTemplate;
    private List<DomainEventSubscriber> domainEventSubscriberList;
    private AOPConfig aopConfig;
    private MessageDialog messageDialog;

    public CarOfferViewConfig(JdbcTemplate jdbcTemplate, List<DomainEventSubscriber> domainEventSubscriberList, AOPConfig aopConfig, MessageDialog messageDialog) {
        this.jdbcTemplate = requireNonNull(jdbcTemplate);
        this.domainEventSubscriberList = requireNonNull(domainEventSubscriberList);
        this.aopConfig = requireNonNull(aopConfig);
        this.messageDialog = requireNonNull(messageDialog);
    }

    public CarOfferView getCarOfferView() {
        CarBookingUseCase carBookingUseCase = createCarBookingUseCase(jdbcTemplate);
        CarOfferUseCase carOfferUseCase = createCarOfferUseCase(jdbcTemplate);

        CarOfferUIConfig carOfferUIConfig = new CarOfferUIConfig(messageDialog);
        return carOfferUIConfig.getCarOfferView(carBookingUseCase, carOfferUseCase, messageDialog);
    }

    private CarOfferUseCase createCarOfferUseCase(JdbcTemplate rentalJdbcTemplate) {
        CarOfferComponent carOfferComponent = new CarOfferComponent();
        CarOfferUseCase carOfferUseCase = carOfferComponent.createCarOfferUseCase(rentalJdbcTemplate);
        return aopConfig.applyAOP(carOfferUseCase);
    }

    private CarBookingUseCase createCarBookingUseCase(JdbcTemplate rentalJdbcTemplate) {
        CarBookingComponent carBookingComponent = new CarBookingComponent();
        CarBookingUseCase carBookingUseCase = carBookingComponent.getCarBookingUseCase(rentalJdbcTemplate, domainEventSubscriberList);
        return aopConfig.applyAOP(carBookingUseCase);
    }
}
