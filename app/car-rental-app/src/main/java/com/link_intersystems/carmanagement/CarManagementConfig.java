package com.link_intersystems.carmanagement;

import com.link_intersystems.carrental.management.CarManagementView;
import com.link_intersystems.carrental.management.bookings.ListCarBookingView;

public class CarManagementConfig {

    public CarManagementView getCarManagementView(ListCarBookingView listCarBookingView) {
        CarManagementView carManagementView = new CarManagementView();
        carManagementView.setListCarBookingsView(listCarBookingView);
        return carManagementView;
    }
}
