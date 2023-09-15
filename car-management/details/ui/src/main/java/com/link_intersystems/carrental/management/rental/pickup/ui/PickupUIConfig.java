package com.link_intersystems.carrental.management.rental.pickup.ui;

import com.link_intersystems.carrental.management.rental.PickupCarUseCase;
import com.link_intersystems.carrental.swing.notification.MessageDialog;

public class PickupUIConfig {

    public PickupCarController getPickupCarController(MessageDialog messageDialog, PickupCarUseCase pickupCarUseCase) {
        return new PickupCarController(pickupCarUseCase, messageDialog);
    }

}
