package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.offer.CarOfferModel;
import com.link_intersystems.carrental.swing.notification.MessageDialog;
import com.link_intersystems.swing.action.AbstractTaskAction;
import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.swing.action.TaskProgress;
import com.link_intersystems.swing.selection.Selection;
import com.link_intersystems.swing.selection.SelectionListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static java.util.Objects.*;

public class CarBookingController extends AbstractTaskAction<CarBookingResponseModel, Void> {

    private CarBookingUseCase carBookingUseCase;
    private MessageDialog messageDialog;

    private Selection<CarOfferModel> carOfferSelection = Selection.empty();

    private ActionTrigger actionTrigger = new ActionTrigger(this);
    private Optional<ActionListener> onDoneActionListener = Optional.empty();

    private SelectionListener<CarOfferModel> carOfferModelSelectionListener = event -> {
        Selection<CarOfferModel> selection = event.getNewSelection();
        setCarOfferSelection(selection);
    };

    public CarBookingController(CarBookingUseCase carBookingUseCase, MessageDialog messageDialog) {
        this.carBookingUseCase = requireNonNull(carBookingUseCase);
        this.messageDialog = requireNonNull(messageDialog);

        putValue(Action.NAME, "Book");
        updateEnablement();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isEnabled()) {
            super.actionPerformed(e);
        }
    }

    public void setOnDoneActionListener(ActionListener onDoneActionListener) {
        this.onDoneActionListener = Optional.ofNullable(onDoneActionListener);
    }

    private void setCarOfferSelection(Selection<CarOfferModel> carOfferSelection) {
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
    protected CarBookingResponseModel doInBackground(TaskProgress<Void> backgroundProgress) throws Exception {
        CarOfferModel carToBook = carOfferSelection.getFirstElement();
        CarBookingRequestModel requestModel = new CarBookingRequestModel();
        requestModel.setCustomerId(1);
        requestModel.setCarId(carToBook.getId());


        requestModel.setPickUpDateTime(carToBook.getPickupDateTime());
        requestModel.setReturnDateTime(carToBook.getReturnDateTime());
        return carBookingUseCase.bookCar(requestModel);
    }

    @Override
    protected void done(CarBookingResponseModel result) {
        onDoneActionListener.ifPresent(actionTrigger::performAction);
        messageDialog.showInfo("Car successfully booked. Booking number: " + result.getBookingNumber());
        updateEnablement();
    }

    @Override
    protected void failed(ExecutionException e) {
        messageDialog.showException(e.getCause());
        updateEnablement();
    }
}
