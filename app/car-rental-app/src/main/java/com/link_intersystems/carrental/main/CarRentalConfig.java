package com.link_intersystems.carrental.main;

import com.link_intersystems.carrental.management.CarManagementView;
import com.link_intersystems.carrental.management.CarManagementViewConfig;
import com.link_intersystems.carrental.offer.CarOfferViewConfig;
import com.link_intersystems.carrental.offer.ui.CarOfferView;
import com.link_intersystems.carrental.swing.notification.MessageDialog;
import com.link_intersystems.carrental.ui.CarRentalMainFrame;
import com.link_intersystems.carrental.ui.CarRentalMainUIConfig;

import static java.util.Objects.*;

public class CarRentalConfig {

    private CarOfferViewConfig carOfferViewConfig;
    private CarManagementViewConfig carManagementViewConfig;
    private MessageDialog messageDialog;
    private CarRentalMainFrame mainFrame;

    public CarRentalConfig(CarOfferViewConfig carOfferViewConfig, CarManagementViewConfig carManagementViewConfig, MessageDialog messageDialog) {
        this.carOfferViewConfig = requireNonNull(carOfferViewConfig);
        this.carManagementViewConfig = requireNonNull(carManagementViewConfig);
        this.messageDialog = requireNonNull(messageDialog);
    }

    public CarRentalMainFrame getMainFrame() {
        if (mainFrame == null) {
            CarRentalMainUIConfig carRentalMainUIConfig = new CarRentalMainUIConfig();

            CarManagementView carManagementView = carManagementViewConfig.getCarManagementView();
            CarOfferView carOfferView = carOfferViewConfig.getCarOfferView();

            mainFrame = carRentalMainUIConfig.getMainFrame(carOfferView, carManagementView);
        }
        return mainFrame;
    }
}
