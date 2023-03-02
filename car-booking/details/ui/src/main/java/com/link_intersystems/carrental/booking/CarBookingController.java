package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.MessageDialog;
import com.link_intersystems.swing.action.AbstractWorkerAction;
import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.swing.action.BackgroundProgress;
import com.link_intersystems.swing.selection.Selection;
import com.link_intersystems.swing.selection.SelectionListener;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class CarBookingController extends AbstractWorkerAction<CarBookingResponseModel, Void> {

    private CarBookingUseCase carBookingUseCase;
    private CarSearchModel carSearchModel;
    private MessageDialog messageDialog;

    private Selection<CarOfferModel> carOfferSelection = Selection.empty();

    private ActionTrigger actionTrigger = new ActionTrigger(this);
    private Optional<ActionListener> onDoneActionListener = Optional.empty();

    private SelectionListener<CarOfferModel> carOfferModelSelectionListener = event -> {
        Selection<CarOfferModel> selection = event.getSelection();
        setCarOfferSelection(selection);
    };

    public CarBookingController(CarBookingUseCase carBookingUseCase, CarSearchModel carSearchModel, MessageDialog messageDialog) {
        this.carBookingUseCase = carBookingUseCase;
        this.carSearchModel = carSearchModel;
        this.messageDialog = messageDialog;

        putValue(Action.NAME, "Book");
        updateEnablement();
    }

    public void setOnDoneActionListener(ActionListener onDoneActionListener) {
        this.onDoneActionListener = Optional.ofNullable(onDoneActionListener);
    }

    public void setCarOfferSelection(Selection<CarOfferModel> carOfferSelection) {
        this.carOfferSelection = carOfferSelection;
        updateEnablement();
    }

    private void updateEnablement() {
        boolean enabled = !carOfferSelection.isEmpty();
        setEnabled(enabled);
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


        requestModel.setPickUpDateTime(LocalDateTime.parse(carSearchModel.getPickupDate().getValue()));
        requestModel.setReturnDateTime(LocalDateTime.parse(carSearchModel.getReturnDate().getValue()));
        return carBookingUseCase.bookCar(requestModel);
    }

    @Override
    protected void done(CarBookingResponseModel result) {
        onDoneActionListener.ifPresent(actionTrigger::performAction);
        messageDialog.showInfo("Car successfully booked. Booking number: " + result.getBookingNumber());
    }

    @Override
    protected void failed(ExecutionException e) {
        messageDialog.showException(e.getCause());
    }
}
