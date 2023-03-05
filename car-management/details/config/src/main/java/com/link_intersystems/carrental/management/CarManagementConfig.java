package com.link_intersystems.carrental.management;

import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingView;

public class CarManagementConfig {

    public CarManagementView getCarManagementView(ListCarBookingView listCarBookingView) {
        CarManagementView carManagementView = new CarManagementView();
        carManagementView.setListCarBookingsView(listCarBookingView);
        return carManagementView;
    }
}
