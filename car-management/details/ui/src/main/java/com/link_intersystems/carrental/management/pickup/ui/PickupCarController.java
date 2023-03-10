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

        PickupCarModelPresenter presenter = new PickupCarModelPresenter();
        ListCarBookingModel firstElement = listCarBookingModelSelection.getFirstElement();
        PickupCarModel pickupCarModel = presenter.toPickupCarModel(firstElement);


        PickupCarForm<PickupCarModel> pickupCarForm = new PickupCarForm<>();
        pickupCarForm.setModel(pickupCarModel);

        int pickupCar = messageDialog.showDialog("Pickup Car", pickupCarForm.getComponent());
        if (pickupCar == OK_OPTION) {

            PickupCarRequestModel requestModel = presenter.toRequestModel(pickupCarModel);

            pickupCarUseCase.pickupCar(requestModel);
        }
    }
}
