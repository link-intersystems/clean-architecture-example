package com.link_intersystems.car;

import com.link_intersystems.car.offers.CarOfferConfig;
import com.link_intersystems.car.offers.ui.CarOfferComponentConfig;
import com.link_intersystems.car.ui.MainFrame;
import com.link_intersystems.car.ui.MainComponentConfig;

public class CarRentalApp {

    public static void main(String[] args) {
        CarRentalApp carRentalApp = new CarRentalApp();
        carRentalApp.run();
    }

    private void run() {
        CarOfferConfig carOfferUseCaseConfig = new CarOfferConfig();
        CarOfferComponentConfig carOfferComponentConfig = new CarOfferComponentConfig(carOfferUseCaseConfig);
        MainComponentConfig mainComponentConfig = new MainComponentConfig(carOfferComponentConfig);
        MainFrame mainFrame = mainComponentConfig.getMainComponent();
        mainFrame.show();
    }
}
