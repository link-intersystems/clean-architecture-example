package com.link_intersystems.carrental.ui;

import com.link_intersystems.carrental.management.CarManagementView;
import com.link_intersystems.carrental.offer.ui.CarOfferView;

public class CarRentalMainUIConfig {

    public CarRentalMainFrame getMainFrame(CarOfferView carOfferView, CarManagementView carManagementView) {
        CarRentalMainFrame carRentalMainFrame = new CarRentalMainFrame();

        carRentalMainFrame.setCarOfferView(carOfferView);
        carRentalMainFrame.setCarManagementView(carManagementView);

        return carRentalMainFrame;
    }


}
