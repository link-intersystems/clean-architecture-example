package com.link_intersystems.car.offers.ui;

import com.link_intersystems.car.offers.CarOfferConfig;
import com.link_intersystems.car.offers.CarOffersUseCase;

public class CarOfferComponentConfig {

    private com.link_intersystems.car.offers.CarOfferConfig carOfferConfig;

    public CarOfferComponentConfig(CarOfferConfig carOfferConfig) {
        this.carOfferConfig = carOfferConfig;
    }

    public CarOfferView getCarOfferView() {
        CarOffersUseCase carOffersUseCase = carOfferConfig.getCarOfferUseCase();
        CarOfferController carOfferController = new CarOfferController(carOffersUseCase);

        CarOfferView carOfferView = new CarOfferView();
        carOfferView.setCarOfferListModel(carOfferController.getCarOfferListModel());
        carOfferView.setCarSearchModel(carOfferController.getCarSearchModel());
        carOfferView.setCarSearchRunnable(carOfferController::searchCars);
        return carOfferView;
    }
}
