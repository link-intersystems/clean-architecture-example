package com.link_intersystems.carrental.management.rental.pickup.list.ui;

import com.link_intersystems.carrental.management.rental.ListPickupCarUseCase;
import com.link_intersystems.carrental.management.rental.returnCar.ui.ReturnCarFormController;

public class ListPickupCarUIConfig {

    public ListPickupCarView getListPickupCarView(ListPickupCarController listPickupCarController, ReturnCarFormController returnCarFormController) {
        ListPickupCarView listPickupCarView = new ListPickupCarView();

        listPickupCarView.addToolbarAction(listPickupCarController);
        listPickupCarView.setListCarBookingModel(listPickupCarController.getPickupCarListModel());

        listPickupCarView.addToolbarAction(returnCarFormController);
        listPickupCarView.addSelectionChangedListener(listPickupCarController);

        return listPickupCarView;
    }

    public ListPickupCarController getPickupCarListController(ListPickupCarUseCase listPickupCarUseCase) {
        return new ListPickupCarController(listPickupCarUseCase);
    }

}
