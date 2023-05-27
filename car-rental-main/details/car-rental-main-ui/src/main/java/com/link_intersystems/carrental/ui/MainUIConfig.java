package com.link_intersystems.carrental.ui;

import com.link_intersystems.carrental.management.CarManagementView;
import com.link_intersystems.carrental.offer.ui.CarOfferView;

public class MainUIConfig {

    public MainFrame getMainFrame(CarOfferView carOfferView, CarManagementView carManagementView) {
        MainFrame mainFrame = new MainFrame();

        mainFrame.setCarOfferView(carOfferView);
        mainFrame.setCarManagementView(carManagementView);

        return mainFrame;
    }


}
