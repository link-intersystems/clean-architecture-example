package com.link_intersystems.carrental.ui;

import com.link_intersystems.carrental.management.CarManagementView;
import com.link_intersystems.carrental.offer.CarOfferView;
import com.link_intersystems.carrental.swing.notification.DefaultMessageDialog;

public class CarRentalMainUIConfig {

    public CarRentalMainFrame getMainFrame(CarOfferView carOfferView, CarManagementView carManagementView) {
        CarRentalMainFrame carRentalMainFrame = new CarRentalMainFrame();

        carRentalMainFrame.setCarOfferView(carOfferView);
        carRentalMainFrame.setCarManagementView(carManagementView);

        return carRentalMainFrame;
    }

    public DefaultMessageDialog getMessageDialog() {
        DefaultMessageDialog defaultMessageDialog = new DefaultMessageDialog();
        return defaultMessageDialog;
    }

    public void initUI(CarRentalMainFrame carRentalMainFrame, DefaultMessageDialog messageDialog) {

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> messageDialog.showException(e));

        messageDialog.setParentComponent(carRentalMainFrame.getComponent());
    }

}
