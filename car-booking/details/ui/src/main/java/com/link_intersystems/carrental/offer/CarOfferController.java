package com.link_intersystems.carrental.offer;

import com.link_intersystems.carrental.swing.notification.MessageDialog;
import com.link_intersystems.swing.action.AbstractWorkerAction;
import com.link_intersystems.swing.action.BackgroundProgress;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CarOfferController extends AbstractWorkerAction<List<CarOfferOutputModel>, Void> {

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
    protected List<CarOfferOutputModel> doInBackground(BackgroundProgress<Void> backgroundProgress) throws Exception {
        CarOfferRequestModel requestModel = carOfferPresenter.toRequestModel(carSearchModel);
        return carOfferUseCase.makeOffers(requestModel);
    }

    @Override
    protected void done(List<CarOfferOutputModel> response) {
        carOfferListModel.clear();

        for (CarOfferOutputModel carOffer : response) {
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
