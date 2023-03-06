package com.link_intersystems.carrental.management.pickup;

import com.link_intersystems.carrental.management.pickup.ui.PickupCarView;
import com.link_intersystems.carrental.management.pickup.ui.PreparePickupCarController;
import com.link_intersystems.carrental.swing.notification.MessageDialog;

public class PickupConfig {

    public PreparePickupCarController getPreparePickupCarController(MessageDialog messageDialog, PickupCarView pickupCarView) {
        return new PreparePickupCarController(pickupCarView, messageDialog);
    }

    public PickupCarView getPickupCarView() {
        return new PickupCarView();
    }
}
