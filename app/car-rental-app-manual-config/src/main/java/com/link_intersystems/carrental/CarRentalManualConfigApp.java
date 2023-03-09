package com.link_intersystems.carrental;

import com.link_intersystems.carrental.ui.CarRentalMainFrame;

public class CarRentalManualConfigApp {

    public static void main(String[] args) {
        CarRentalManualConfigApp carRentalApp = new CarRentalManualConfigApp();
        carRentalApp.run(args);
    }

    void run(String[] args) {
        CarRentalManualConfig carRentalManualConfig = new CarRentalManualConfig();
        CarRentalMainFrame mainFrame = carRentalManualConfig.createMainFrame();
        openFrame(mainFrame);
    }

    protected void openFrame(CarRentalMainFrame mainFrame) {
        mainFrame.show();
    }

}
