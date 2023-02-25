package com.link_intersystems.carrental;

import com.link_intersystems.plugins.ApplicationContext;

public class CarRentalApp {

    public static void main(String[] args) {
        CarRentalApp carRentalApp = new CarRentalApp();
        carRentalApp.run();
    }

    private void run() {
        ApplicationContext applicationContext = new ApplicationContext();
        MainFrame mainFrame = applicationContext.getService(MainFrame.class);
        mainFrame.show();
    }
}
