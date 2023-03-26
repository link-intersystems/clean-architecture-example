package com.link_intersystems.carrental.offer.ui;

import com.link_intersystems.carrental.offer.CarOfferResponseModel;
import com.link_intersystems.carrental.offer.CarOfferRequestModel;
import com.link_intersystems.carrental.offer.CarOfferUseCase;
import com.link_intersystems.carrental.swing.notification.MessageDialog;
import com.link_intersystems.swing.action.AbstractTaskAction;
import com.link_intersystems.swing.action.TaskProgress;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CarOfferController extends AbstractTaskAction<List<CarOfferResponseModel>, Void> {

    private CarOfferUseCase carOfferUseCase;

    private DefaultListModel<CarOfferModel> carOfferListModel = new DefaultListModel<>();

    private CarSearchModel carSearchModel = new CarSearchModel();
    private CarOfferPresenter carOfferPresenter = new CarOfferPresenter();
    private MessageDialog messageDialog;

    public CarOfferController(CarOfferUseCase carOfferUseCase) {
        this.carOfferUseCase = carOfferUseCase;

        putValue(Action.NAME, "Search");
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
    protected List<CarOfferResponseModel> doInBackground(TaskProgress<Void> backgroundProgress) throws Exception {
        CarOfferRequestModel requestModel = carOfferPresenter.toRequestModel(carSearchModel);
        return carOfferUseCase.makeOffers(requestModel);
    }

    @Override
    protected void done(List<CarOfferResponseModel> response) {
        carOfferListModel.clear();

        for (CarOfferResponseModel carOffer : response) {
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
