package com.link_intersystems.carrental.management.pickup.ui;

import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingModel;
import com.link_intersystems.carrental.management.pickup.PickupCarRequestModel;
import com.link_intersystems.carrental.management.pickup.PickupCarUseCase;
import com.link_intersystems.carrental.swing.notification.MessageDialog;
import com.link_intersystems.swing.selection.Selection;
import com.link_intersystems.swing.selection.SelectionChangeEvent;
import com.link_intersystems.swing.selection.SelectionListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;

import static com.link_intersystems.carrental.swing.action.ActionConstants.*;
import static javax.swing.JOptionPane.*;

public class PickupCarController extends AbstractAction implements SelectionListener<ListCarBookingModel> {

    private PickupCarUseCase pickupCarUseCase;
    private MessageDialog messageDialog;
    private Selection<ListCarBookingModel> listCarBookingModelSelection;

    public PickupCarController(PickupCarUseCase pickupCarUseCase, MessageDialog messageDialog) {
        this.pickupCarUseCase = pickupCarUseCase;
        this.messageDialog = messageDialog;

        putValue(Action.NAME, "Pickup Car");
        putValue(ACTION_GROUP_KEY, "pickup");

        setEnabled(false);
    }

    @Override
    public void selectionChanged(SelectionChangeEvent<ListCarBookingModel> event) {
        listCarBookingModelSelection = event.getNewSelection();
        setEnabled(!listCarBookingModelSelection.isEmpty());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (listCarBookingModelSelection.isEmpty()) {
            return;
        }

        PickupCarModel pickupCarModel = new PickupCarModel();

        ListCarBookingModel listCarBookingModel = listCarBookingModelSelection.getFirstElement();
        pickupCarModel.setVin(listCarBookingModel.getVin());
        pickupCarModel.setBookingNumber(listCarBookingModel.getBookingNumber());
        pickupCarModel.setPickupDate(LocalDateTime.now());

        PickupCarForm<PickupCarModel> pickupCarForm = new PickupCarForm<>();
        pickupCarForm.setModel(pickupCarModel);

        int pickupCar = messageDialog.showDialog("Pickup Car", pickupCarForm.getComponent());
        if (pickupCar == OK_OPTION) {


            PickupCarRequestModel pickupCarRequestModel = new PickupCarRequestModel();
            pickupCarRequestModel.setCarId(listCarBookingModel.getVin());
            pickupCarRequestModel.setPickupDataTime(pickupCarModel.getPickupDate());
            int value = pickupCarModel.getFuelLevel().getValue();
            pickupCarRequestModel.setFuelLevel(FuelLevel.ofPercentage(value));

            pickupCarUseCase.pickupCar(pickupCarRequestModel);
        }
    }
}
