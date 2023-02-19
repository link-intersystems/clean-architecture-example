package com.link_intersystems.car.offers.ui;

import com.link_intersystems.car.offers.CarOffersUseCase;

public class CarOfferComponent {

    private final CarOfferController carOfferController;
    private final CarOfferView carOfferView;

    CarOfferComponent(CarOffersUseCase carOfferUseCase) {
        carOfferController = new CarOfferController(carOfferUseCase);

        carOfferView = new CarOfferView();
        carOfferView.setCarOfferListModel(carOfferController.getCarOfferListModel());
        carOfferView.setCarSearchModel(carOfferController.getCarSearchModel());
        carOfferView.setCarSearchRunnable(carOfferController::searchCars);
    }

    public CarOfferView getCarOfferView() {
        return carOfferView;
    }
}
