package com.link_intersystems.carrental.management.returnCar.ui;

import com.link_intersystems.carrental.management.pickup.get.GetPickupCarUseCase;
import com.link_intersystems.carrental.management.pickup.list.ui.ListPickupCarController;
import com.link_intersystems.carrental.swing.notification.MessageDialog;

public class ReturnCarUIConfig {

    public ReturnCarFormController getReturnCarController(GetPickupCarUseCase getPickupCarUseCase, MessageDialog messageDialog, ListPickupCarController listPickupCarController) {
        ReturnCarFormController returnCarFormController = new ReturnCarFormController(getPickupCarUseCase, messageDialog);
        listPickupCarController.addBookingNumberSelectionListener(returnCarFormController);
        return returnCarFormController;
    }

}
