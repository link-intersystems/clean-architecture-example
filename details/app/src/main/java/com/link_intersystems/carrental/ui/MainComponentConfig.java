package com.link_intersystems.carrental.ui;

import com.link_intersystems.carrental.MainFrame;
import com.link_intersystems.carrental.offers.CarOfferComponentConfig;
import com.link_intersystems.carrental.booking.CarOfferView;
import com.link_intersystems.plugins.ApplicationContext;

public class MainComponentConfig {

    private CarOfferComponentConfig carOfferComponentConfig;

    public MainComponentConfig(CarOfferComponentConfig carOfferComponentConfig) {
        this.carOfferComponentConfig = carOfferComponentConfig;
    }

    public MainFrame getMainComponent(ApplicationContext applicationContext) {
        MainFrame mainFrame = new MainFrame();
        CarOfferView carOfferView = carOfferComponentConfig.getCarOfferView(applicationContext, mainFrame.getMessageDialog());
        mainFrame.setCarOfferView(carOfferView);
        return mainFrame;
    }
}
