package com.link_intersystems.carrental.offer.ui;

import javax.swing.*;

public class CarOfferSearchArgumentsViewControl {
    private CarOfferSearchArgumentsView carOfferSearchArgumentsView;

    public CarOfferSearchArgumentsViewControl(CarOfferSearchArgumentsView carOfferSearchArgumentsView) {
        this.carOfferSearchArgumentsView = carOfferSearchArgumentsView;
    }

    public String getVehicleType() {
        JComboBox<String> vehicleType = carOfferSearchArgumentsView.getVehicleType();
        return (String) vehicleType.getSelectedItem();
    }
}
