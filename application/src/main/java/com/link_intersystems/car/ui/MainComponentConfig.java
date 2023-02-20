package com.link_intersystems.car.ui;

import com.link_intersystems.car.offers.ui.CarOfferComponentConfig;
import com.link_intersystems.car.offers.ui.CarOfferView;

public class MainComponentConfig {

    private CarOfferComponentConfig carOfferComponentConfig;

    public MainComponentConfig(CarOfferComponentConfig carOfferComponentConfig) {
        this.carOfferComponentConfig = carOfferComponentConfig;
    }

    public MainFrame getMainComponent() {
        CarOfferView carOfferView = carOfferComponentConfig.getCarOfferView();
        return new MainFrame(carOfferView);
    }
}
