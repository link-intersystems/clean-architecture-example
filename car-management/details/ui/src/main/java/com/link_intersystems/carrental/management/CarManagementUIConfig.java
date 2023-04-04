package com.link_intersystems.carrental.management;

import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingView;
import com.link_intersystems.carrental.management.rental.pickup.list.ui.ListPickupCarView;

public class CarManagementUIConfig {

    public CarManagementView getCarManagementView(ListCarBookingView listCarBookingView, ListPickupCarView listPickupCarView) {
        CarManagementView carManagementView = new CarManagementView();
        carManagementView.setListCarBookingsView(listCarBookingView);
        carManagementView.setListPickupCarView(listPickupCarView);
        return carManagementView;
    }
}
