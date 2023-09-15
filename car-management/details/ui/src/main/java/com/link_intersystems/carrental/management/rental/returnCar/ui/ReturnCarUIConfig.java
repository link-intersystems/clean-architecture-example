package com.link_intersystems.carrental.management.rental.returnCar.ui;

import com.link_intersystems.carrental.management.rental.GetPickupCarUseCase;
import com.link_intersystems.carrental.management.rental.ReturnCarUseCase;
import com.link_intersystems.carrental.management.rental.pickup.list.ui.ListPickupCarController;
import com.link_intersystems.carrental.swing.notification.MessageDialog;

public class ReturnCarUIConfig {

    public ReturnCarFormController getReturnCarController(GetPickupCarUseCase getPickupCarUseCase, MessageDialog messageDialog, ListPickupCarController listPickupCarController, ReturnCarUseCase returnCarUseCase) {
        ReturnCarFormController returnCarFormController = new ReturnCarFormController(getPickupCarUseCase, messageDialog, returnCarUseCase);
        listPickupCarController.addBookingNumberSelectionListener(returnCarFormController);
        returnCarFormController.setAfterCarReturnedActionListener(listPickupCarController);
        return returnCarFormController;
    }

}
