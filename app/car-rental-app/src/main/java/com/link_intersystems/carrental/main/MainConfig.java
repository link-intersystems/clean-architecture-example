package com.link_intersystems.carrental.main;

import com.link_intersystems.carrental.management.CarManagementView;
import com.link_intersystems.carrental.management.CarManagementViewConfig;
import com.link_intersystems.carrental.offer.CarOfferViewConfig;
import com.link_intersystems.carrental.offer.ui.CarOfferView;
import com.link_intersystems.carrental.ui.MainFrame;
import com.link_intersystems.carrental.ui.MainUIConfig;

import static java.util.Objects.*;

public class MainConfig {

    private CarOfferViewConfig carOfferViewConfig;
    private CarManagementViewConfig carManagementViewConfig;
    private MainFrame mainFrame;

    public MainConfig(CarOfferViewConfig carOfferViewConfig, CarManagementViewConfig carManagementViewConfig) {
        this.carOfferViewConfig = requireNonNull(carOfferViewConfig);
        this.carManagementViewConfig = requireNonNull(carManagementViewConfig);
    }

    public MainFrame getMainFrame() {
        if (mainFrame == null) {
            MainUIConfig mainUIConfig = new MainUIConfig();

            CarManagementView carManagementView = carManagementViewConfig.getCarManagementView();
            CarOfferView carOfferView = carOfferViewConfig.getCarOfferView();

            mainFrame = mainUIConfig.getMainFrame(carOfferView, carManagementView);
        }
        return mainFrame;
    }
}
