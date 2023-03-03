package com.link_intersystems.carmanagement;

import com.link_intersystems.carmanagement.booking.ListBookingsUseCase;
import com.link_intersystems.carrental.management.CarManagementView;
import com.link_intersystems.carrental.management.bookings.ListCarBookingView;

public class CarManagementConfig {

    public CarManagementView getCarManagementView(ListBookingsUseCase listBookingsUseCase) {
        CarManagementView carManagementView = new CarManagementView();

        ListCarBookingView listCarBookingView = new ListCarBookingView(listBookingsUseCase);
        carManagementView.setListCarBookingsView(listCarBookingView);

        return carManagementView;
    }
}
