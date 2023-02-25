package com.link_intersystems.car.ui;

import com.link_intersystems.car.offers.ui.CarOfferComponentConfig;
import com.link_intersystems.car.offers.ui.CarOfferView;

public class MainComponentConfig {

    private CarOfferComponentConfig carOfferComponentConfig;

    public MainComponentConfig(CarOfferComponentConfig carOfferComponentConfig) {
        this.carOfferComponentConfig = carOfferComponentConfig;
    }

    public MainFrame getMainComponent() {
        MainFrame mainFrame = new MainFrame();
        CarOfferView carOfferView = carOfferComponentConfig.getCarOfferView(mainFrame.getMessageDialog());
        mainFrame.setCarOfferView(carOfferView);
        return mainFrame;
    }
}
