package com.link_intersystems.car.offers.ui;

import com.link_intersystems.car.offers.*;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CarOfferController extends AbstractAction {

    private CarOffersUseCase carOffersUseCase;

    private DefaultListModel<CarOfferModel> carOfferListModel = new DefaultListModel<>();

    private CarSearchModel carSearchModel = new CarSearchModel();
    private CarOfferPresenter carOfferPresenter = new CarOfferPresenter();

    public CarOfferController(CarOffersUseCase carOffersUseCase) {
        this.carOffersUseCase = carOffersUseCase;

        putValue(Action.NAME, "Search");
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
        CarOffersRequestModel requestModel = new CarOffersRequestModel();
        requestModel.setVehicleType(carSearchModel.getVehicleType());
        requestModel.setPickUpDateTime(carSearchModel.getPickupDate());
        requestModel.setReturnDateTime(carSearchModel.getReturnDate());

        CarOffersResponseModel responseModel = carOffersUseCase.makeOffers(requestModel);

        CarOffersOutputModel carOffers = responseModel.getCarOffers();

        carOfferListModel.clear();
        for (CarOfferOutputModel carOffer : carOffers) {
            CarOfferModel carOfferModel = carOfferPresenter.toPresentationModel(carOffer);
            carOfferListModel.addElement(carOfferModel);
        }
    }


}
