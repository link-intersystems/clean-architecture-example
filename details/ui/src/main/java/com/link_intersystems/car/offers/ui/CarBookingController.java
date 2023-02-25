package com.link_intersystems.car.offers.ui;

import com.link_intersystems.car.booking.CarBookingRequestModel;
import com.link_intersystems.car.booking.CarBookingResponseModel;
import com.link_intersystems.car.booking.CarBookingUseCase;
import com.link_intersystems.car.ui.MessageDialog;
import com.link_intersystems.swing.action.AbstractWorkerAction;
import com.link_intersystems.swing.action.BackgroundProgress;
import com.link_intersystems.swing.selection.Selection;
import com.link_intersystems.swing.selection.SelectionListener;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

public class CarBookingController extends AbstractWorkerAction<CarBookingResponseModel, Void> {

    private CarBookingUseCase carBookingUseCase;
    private MessageDialog messageDialog;

    private Selection<CarOfferModel> carOfferSelection = Selection.empty();

    private SelectionListener<CarOfferModel> carOfferModelSelectionListener = event -> {
        Selection<CarOfferModel> selection = event.getSelection();
        setCarOfferSelection(selection);
    };
    private CarSearchModel carSearchModel;

    public CarBookingController(CarBookingUseCase carBookingUseCase, MessageDialog messageDialog) {
        this.carBookingUseCase = carBookingUseCase;
        this.messageDialog = messageDialog;

        putValue(Action.NAME, "Book");
        updateEnablement();
    }

    public void setCarSearchModel(CarSearchModel carSearchModel) {
        this.carSearchModel = carSearchModel;
        updateEnablement();
    }

    public void setCarOfferSelection(Selection<CarOfferModel> carOfferSelection) {
        this.carOfferSelection = carOfferSelection;
        updateEnablement();
    }

    private void updateEnablement() {
        setEnabled(this.carSearchModel != null && !carOfferSelection.isEmpty());
    }

    public SelectionListener<CarOfferModel> getSelectionListener() {
        return carOfferModelSelectionListener;
    }

    @Override
    protected CarBookingResponseModel doInBackground(BackgroundProgress<Void> backgroundProgress) throws Exception {
        CarOfferModel carToBook = carOfferSelection.getFirstElement();
        CarBookingRequestModel requestModel = new CarBookingRequestModel();
        requestModel.setCarId(carToBook.getId());
        requestModel.setPickUpDateTime(LocalDateTime.parse(carSearchModel.getPickupDate().getValue()));
        requestModel.setReturnDateTime(LocalDateTime.parse(carSearchModel.getReturnDate().getValue()));
        CarBookingResponseModel carBookingResponseModel = carBookingUseCase.bookCar(requestModel);

        CarBookingResponseModel responseModel = new CarBookingResponseModel();
        return responseModel;
    }

    @Override
    protected void done(CarBookingResponseModel result) {

    }

    @Override
    protected void failed(ExecutionException e) {
        messageDialog.showException(e.getCause());
    }
}
