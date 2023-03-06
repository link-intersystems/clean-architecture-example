package com.link_intersystems.carrental.management.pickup.ui;

import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingModel;
import com.link_intersystems.carrental.swing.notification.MessageDialog;
import com.link_intersystems.swing.selection.SelectionChangeEvent;
import com.link_intersystems.swing.selection.SelectionListener;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static com.link_intersystems.carrental.swing.action.ActionConstants.*;
import static javax.swing.JOptionPane.*;

public class PreparePickupCarController extends AbstractAction implements SelectionListener<ListCarBookingModel> {

    private PickupCarView pickupCarView;
    private MessageDialog messageDialog;

    public PreparePickupCarController(PickupCarView pickupCarView, MessageDialog messageDialog) {
        this.pickupCarView = pickupCarView;
        this.messageDialog = messageDialog;

        putValue(Action.NAME, "Pickup Car");
        putValue(ACTION_GROUP_KEY, "pickup");

        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int pickupCar = messageDialog.showDialog("Pickup Car", pickupCarView.getViewComponent());
        if (pickupCar == OK_OPTION) {
            messageDialog.showInfo("Car picked up");
        }
    }

    @Override
    public void selectionChanged(SelectionChangeEvent<ListCarBookingModel> event) {
        setEnabled(!event.getNewSelection().isEmpty());
    }
}
