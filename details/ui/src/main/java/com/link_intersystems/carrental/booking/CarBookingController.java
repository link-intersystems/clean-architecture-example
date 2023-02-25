package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.MessageDialog;
import com.link_intersystems.swing.action.AbstractWorkerAction;
import com.link_intersystems.swing.action.ActionTrigger;
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

    private ActionTrigger actionTrigger = new ActionTrigger(this);

    private SelectionListener<CarOfferModel> carOfferModelSelectionListener = event -> {
        Selection<CarOfferModel> selection = event.getSelection();
        setCarOfferSelection(selection);
    };
    private CarOfferController carOfferController;

    public CarBookingController(CarBookingUseCase carBookingUseCase, MessageDialog messageDialog, CarOfferController carOfferController) {
        this.carBookingUseCase = carBookingUseCase;
        this.messageDialog = messageDialog;
        this.carOfferController = carOfferController;

        putValue(Action.NAME, "Book");
        updateEnablement();
    }

    public void setCarOfferSelection(Selection<CarOfferModel> carOfferSelection) {
        this.carOfferSelection = carOfferSelection;
        updateEnablement();
    }

    private void updateEnablement() {
        setEnabled(!carOfferSelection.isEmpty());
    }

    public SelectionListener<CarOfferModel> getSelectionListener() {
        return carOfferModelSelectionListener;
    }

    @Override
    protected CarBookingResponseModel doInBackground(BackgroundProgress<Void> backgroundProgress) throws Exception {
        CarOfferModel carToBook = carOfferSelection.getFirstElement();
        CarBookingRequestModel requestModel = new CarBookingRequestModel();
        requestModel.setCustomerId(1);
        requestModel.setCarId(carToBook.getId());

        CarSearchModel carSearchModel = carOfferController.getCarSearchModel();
        requestModel.setPickUpDateTime(LocalDateTime.parse(carSearchModel.getPickupDate().getValue()));
        requestModel.setReturnDateTime(LocalDateTime.parse(carSearchModel.getReturnDate().getValue()));
        CarBookingResponseModel carBookingResponseModel = carBookingUseCase.bookCar(requestModel);
        return carBookingResponseModel;
    }

    @Override
    protected void done(CarBookingResponseModel result) {
        actionTrigger.performAction(carOfferController);
        messageDialog.showInfo("Car successfully booked. Booking number: " + result.getBookingNumber());
    }

    @Override
    protected void failed(ExecutionException e) {
        messageDialog.showException(e.getCause());
    }
}
