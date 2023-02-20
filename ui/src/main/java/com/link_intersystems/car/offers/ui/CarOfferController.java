package com.link_intersystems.car.offers.ui;

import com.link_intersystems.car.offers.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;

public class CarOfferController extends AbstractAction {

    private CarOffersUseCase carOffersUseCase;

    private DefaultListModel<CarOfferModel> carOfferListModel = new DefaultListModel<>();

    private CarSearchModel carSearchModel = new CarSearchModel();
    private CarOfferPresenter carOfferPresenter = new CarOfferPresenter();

    public CarOfferController(CarOffersUseCase carOffersUseCase) {
        this.carOffersUseCase = carOffersUseCase;

        putValue(Action.NAME, "Search");

        carSearchModel.setVehicleType("MICRO");
        carSearchModel.setPickupDate(LocalDateTime.now().toString());
        carSearchModel.setReturnDate(LocalDateTime.now().plusDays(1).toString());
    }

    public ListModel<CarOfferModel> getCarOfferListModel() {
        return carOfferListModel;
    }

    public CarSearchModel getCarSearchModel() {
        return carSearchModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        searchCars();
    }

    public void searchCars() {
        CarOffersRequestModel requestModel = carOfferPresenter.toRequestModel(carSearchModel);

        CarOffersResponseModel responseModel = carOffersUseCase.makeOffers(requestModel);

        CarOffersOutputModel carOffers = responseModel.getCarOffers();

        carOfferListModel.clear();
        for (CarOfferOutputModel carOffer : carOffers) {
            CarOfferModel carOfferModel = carOfferPresenter.toCarOfferModel(carOffer);
            carOfferListModel.addElement(carOfferModel);
        }
    }


}
