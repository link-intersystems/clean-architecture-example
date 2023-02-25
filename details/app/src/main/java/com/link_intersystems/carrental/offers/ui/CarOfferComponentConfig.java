package com.link_intersystems.carrental.offers.ui;

import com.link_intersystems.carrental.booking.CarBookingConfig;
import com.link_intersystems.carrental.booking.CarBookingUseCase;
import com.link_intersystems.carrental.offers.CarOfferConfig;
import com.link_intersystems.carrental.offers.CarOffersUseCase;
import com.link_intersystems.carrental.ui.MessageDialog;
import com.link_intersystems.plugins.ApplicationContext;

public class CarOfferComponentConfig {

    private CarOfferConfig carOfferConfig;
    private CarBookingConfig carBookingConfig;

    public CarOfferComponentConfig(CarOfferConfig carOfferConfig, CarBookingConfig carBookingConfig) {
        this.carOfferConfig = carOfferConfig;
        this.carBookingConfig = carBookingConfig;
    }

    public CarOfferView getCarOfferView(ApplicationContext applicationContext, MessageDialog messageDialog) {
        CarOffersUseCase carOffersUseCase = carOfferConfig.getCarOfferUseCase(applicationContext);
        CarOfferController carOfferController = new CarOfferController(carOffersUseCase);
        carOfferController.setMessageDialog(messageDialog);

        CarBookingUseCase carBookingUseCase = carBookingConfig.getCarBookingUseCase(applicationContext);
        CarBookingController carBookingController = new CarBookingController(carBookingUseCase, messageDialog, carOfferController);

        CarOfferView carOfferView = new CarOfferView();
        carOfferView.setCarOfferListModel(carOfferController.getCarOfferListModel());
        carOfferView.setCarSearchModel(carOfferController.getCarSearchModel());
        carOfferView.setCarSearchAction(carOfferController);
        carOfferView.setBookCarAction(carBookingController);
        carOfferView.getSelectionProvider().addSelectionChangedListener(carBookingController.getSelectionListener());
        return carOfferView;
    }
}