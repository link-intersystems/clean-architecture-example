package com.link_intersystems.car.offers.ui;

import com.link_intersystems.car.booking.CarBookingConfig;
import com.link_intersystems.car.booking.CarBookingUseCase;
import com.link_intersystems.car.offers.CarOfferConfig;
import com.link_intersystems.car.offers.CarOffersUseCase;

public class CarOfferComponentConfig {

    private CarOfferConfig carOfferConfig;
    private CarBookingConfig carBookingConfig;

    public CarOfferComponentConfig(CarOfferConfig carOfferConfig, CarBookingConfig carBookingConfig) {
        this.carOfferConfig = carOfferConfig;
        this.carBookingConfig = carBookingConfig;
    }

    public CarOfferView getCarOfferView() {
        CarOffersUseCase carOffersUseCase = carOfferConfig.getCarOfferUseCase();
        CarOfferController carOfferController = new CarOfferController(carOffersUseCase);

        CarBookingUseCase carBookingUseCase = carBookingConfig.getCarBookingUseCase();
        CarBookingController carBookingController = new CarBookingController(carBookingUseCase);

        CarOfferView carOfferView = new CarOfferView();
        carOfferView.setCarOfferListModel(carOfferController.getCarOfferListModel());
        carOfferView.setCarSearchModel(carOfferController.getCarSearchModel());
        carOfferView.setCarSearchAction(carOfferController);
        carOfferView.setBookCarAction(carBookingController);
        carOfferView.getSelectionProvider().addSelectionChangedListener(carBookingController.getSelectionListener());

        return carOfferView;
    }
}
