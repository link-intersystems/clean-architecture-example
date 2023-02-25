package com.link_intersystems.car.offers.ui;

import com.link_intersystems.car.offers.*;
import com.link_intersystems.car.ui.MessageDialog;
import com.link_intersystems.swing.action.AbstractWorkerAction;
import com.link_intersystems.swing.action.BackgroundProgress;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

public class CarOfferController extends AbstractWorkerAction<CarOffersResponseModel, Void> {

    private CarOffersUseCase carOffersUseCase;

    private DefaultListModel<CarOfferModel> carOfferListModel = new DefaultListModel<>();

    private CarSearchModel carSearchModel = new CarSearchModel();
    private CarOfferPresenter carOfferPresenter = new CarOfferPresenter();
    private MessageDialog messageDialog;

    public CarOfferController(CarOffersUseCase carOffersUseCase) {
        this.carOffersUseCase = carOffersUseCase;

        putValue(Action.NAME, "Search");

        carSearchModel.getVehicleType().setValue("MICRO");
        carSearchModel.getPickupDate().setValue(LocalDateTime.now().plusDays(1).toString());
        carSearchModel.getReturnDate().setValue(LocalDateTime.now().plusDays(2).toString());
    }

    public ListModel<CarOfferModel> getCarOfferListModel() {
        return carOfferListModel;
    }

    public CarSearchModel getCarSearchModel() {
        return carSearchModel;
    }

    public void setMessageDialog(MessageDialog messageDialog) {
        this.messageDialog = messageDialog;
    }

    @Override
    protected CarOffersResponseModel doInBackground(BackgroundProgress<Void> backgroundProgress) throws Exception {
        CarOffersRequestModel requestModel = carOfferPresenter.toRequestModel(carSearchModel);
        return carOffersUseCase.makeOffers(requestModel);
    }

    @Override
    protected void done(CarOffersResponseModel responseModel) {
        CarOffersOutputModel carOffers = responseModel.getCarOffers();

        carOfferListModel.clear();
        for (CarOfferOutputModel carOffer : carOffers) {
            CarOfferModel carOfferModel = carOfferPresenter.toCarOfferModel(carOffer);
            carOfferListModel.addElement(carOfferModel);
        }
    }

    @Override
    protected void failed(ExecutionException e) {
        if (messageDialog != null) {
            messageDialog.showException(e.getCause());
        }
    }
}
