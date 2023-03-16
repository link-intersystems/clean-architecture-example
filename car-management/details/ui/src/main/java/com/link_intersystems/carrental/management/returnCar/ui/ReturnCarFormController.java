package com.link_intersystems.carrental.management.returnCar.ui;

import com.link_intersystems.carrental.management.booking.ui.BookingNumberModel;
import com.link_intersystems.carrental.management.pickup.get.GetPickupCarResponseModel;
import com.link_intersystems.carrental.management.pickup.get.GetPickupCarUseCase;
import com.link_intersystems.carrental.swing.notification.MessageDialog;
import com.link_intersystems.swing.action.AbstractWorkerAction;
import com.link_intersystems.swing.action.BackgroundProgress;
import com.link_intersystems.swing.selection.Selection;
import com.link_intersystems.swing.selection.SelectionChangeEvent;
import com.link_intersystems.swing.selection.SelectionListener;

import javax.swing.*;
import java.time.LocalDateTime;

import static com.link_intersystems.carrental.swing.action.ActionConstants.*;

public class ReturnCarFormController extends AbstractWorkerAction<GetPickupCarResponseModel, Void> implements SelectionListener<BookingNumberModel> {

    private ReturnCarModelPresenter presenter = new ReturnCarModelPresenter();
    private GetPickupCarUseCase getPickupCarUseCase;
    private MessageDialog messageDialog;
    private ReturnCarModel returnCarModel = new ReturnCarModel();
    private Selection<BookingNumberModel> selectedModel;

    public ReturnCarFormController(GetPickupCarUseCase getPickupCarUseCase, MessageDialog messageDialog) {
        this.getPickupCarUseCase = getPickupCarUseCase;
        this.messageDialog = messageDialog;

        putValue(Action.NAME, "Return car");
        putValue(ACTION_GROUP_KEY, "return");

        setEnabled(false);
    }

    @Override
    public void selectionChanged(SelectionChangeEvent<BookingNumberModel> event) {
        selectedModel = event.getNewSelection();
        setEnabled(!selectedModel.isEmpty());
    }

    @Override
    protected GetPickupCarResponseModel doInBackground(BackgroundProgress<Void> backgroundProgress) throws Exception {
        BookingNumberModel bookingNumberModel = selectedModel.getFirstElement();
        return getPickupCarUseCase.getPickupCar(bookingNumberModel.getValue());
    }

    @Override
    protected void done(GetPickupCarResponseModel result) {
        ReturnCarForm returnCarForm = new ReturnCarForm();
        returnCarModel.setReturnDate(presenter.formatReturnDate(LocalDateTime.now()));
        returnCarModel.setBookingNumber(Integer.toString(result.getBookingNumber()));
        returnCarModel.setOdometer(Integer.toString(result.getOdometer()));
        returnCarModel.getFuelModel().setValue(result.getFuelLevel().getPercent());
        returnCarForm.setModel(returnCarModel);

        int returnCarResult = messageDialog.showDialog("Return car", returnCarForm.getComponent());
        if (JOptionPane.OK_OPTION == returnCarResult) {
        }
    }
}
