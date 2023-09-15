package com.link_intersystems.carrental.management.booking.list.ui;

import com.link_intersystems.carrental.management.booking.ListBookingsUseCase;
import com.link_intersystems.carrental.management.rental.pickup.ui.PickupCarController;

public class ListCarBookingUIConfig {

    public ListCarBookingController getListCarBookingController(ListBookingsUseCase listBookingsUseCase) {
        return new ListCarBookingController(listBookingsUseCase);
    }

    public ListCarBookingView getListCarBookingView(ListCarBookingController listCarBookingController, PickupCarController pickupCarController) {
        ListCarBookingView listCarBookingView = new ListCarBookingView();

        listCarBookingView.addListCarAction(listCarBookingController);
        listCarBookingView.addListCarAction(pickupCarController);
        listCarBookingView.addSelectionChangedListener(pickupCarController);
        listCarBookingView.setListCarBookingModel(listCarBookingController.getCarBookingListModel());
        pickupCarController.setAfterPickupActionListener(listCarBookingController);

        return listCarBookingView;
    }
}
