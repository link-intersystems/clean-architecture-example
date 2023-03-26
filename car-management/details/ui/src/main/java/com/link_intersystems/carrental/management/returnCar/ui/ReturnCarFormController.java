package com.link_intersystems.carrental.management.returnCar.ui;

import com.link_intersystems.carrental.management.booking.ui.BookingNumberModel;
import com.link_intersystems.carrental.management.rental.FuelLevel;
import com.link_intersystems.carrental.management.rental.pickup.get.GetPickupCarResponseModel;
import com.link_intersystems.carrental.management.rental.pickup.get.GetPickupCarUseCase;
import com.link_intersystems.carrental.management.rental.returnCar.ReturnCarRequestModel;
import com.link_intersystems.carrental.management.rental.returnCar.ReturnCarUseCase;
import com.link_intersystems.carrental.swing.notification.MessageDialog;
import com.link_intersystems.swing.action.AbstractTaskAction;
import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.swing.action.TaskProgress;
import com.link_intersystems.swing.selection.Selection;
import com.link_intersystems.swing.selection.SelectionChangeEvent;
import com.link_intersystems.swing.selection.SelectionListener;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import static com.link_intersystems.carrental.swing.action.ActionConstants.*;
import static java.util.Objects.*;

public class ReturnCarFormController extends AbstractTaskAction<GetPickupCarResponseModel, Void> implements SelectionListener<BookingNumberModel> {

    private ReturnCarModelPresenter presenter = new ReturnCarModelPresenter();
    private GetPickupCarUseCase getPickupCarUseCase;
    private MessageDialog messageDialog;
    private ReturnCarUseCase returnCarUseCase;
    private ReturnCarModel returnCarModel = new ReturnCarModel();
    private Selection<BookingNumberModel> selectedModel;
    private ActionTrigger actionTrigger = new ActionTrigger(this);
    private ActionListener afterCarReturnedActionListener = e -> {
    };

    public ReturnCarFormController(GetPickupCarUseCase getPickupCarUseCase, MessageDialog messageDialog, ReturnCarUseCase returnCarUseCase) {
        this.getPickupCarUseCase = getPickupCarUseCase;
        this.messageDialog = messageDialog;
        this.returnCarUseCase = returnCarUseCase;

        putValue(Action.NAME, "Return car");
        putValue(ACTION_GROUP_KEY, "return");

        setEnabled(false);
    }

    public void setAfterCarReturnedActionListener(ActionListener afterCarReturnedActionListener) {
        this.afterCarReturnedActionListener = requireNonNull(afterCarReturnedActionListener);
    }

    @Override
    public void selectionChanged(SelectionChangeEvent<BookingNumberModel> event) {
        selectedModel = event.getNewSelection();
        setEnabled(!selectedModel.isEmpty());
    }

    @Override
    protected GetPickupCarResponseModel doInBackground(TaskProgress<Void> backgroundProgress) throws Exception {
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

            Action returnCarAction = new AbstractTaskAction<Void, Void>() {

                @Override
                protected Void doInBackground(TaskProgress<Void> backgroundProgress) throws Exception {
                    ReturnCarRequestModel requestModel = new ReturnCarRequestModel();
                    String returnDate = returnCarModel.getReturnDate();
                    requestModel.setReturnDateTime(LocalDateTime.parse(returnDate));
                    requestModel.setFuelLevel(FuelLevel.ofPercentage(returnCarModel.getFuelModel().getValue()));
                    requestModel.setBookingNumber(Integer.parseInt(returnCarModel.getBookingNumber()));
                    requestModel.setOdometer(Integer.parseInt(returnCarModel.getOdometer()));

                    returnCarUseCase.returnCar(requestModel);
                    return null;
                }

                @Override
                protected void done(Void result) {
                    actionTrigger.performAction(afterCarReturnedActionListener);
                }
            };
            actionTrigger.performAction(returnCarAction);
        }
    }
}
