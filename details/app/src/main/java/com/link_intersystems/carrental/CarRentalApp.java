package com.link_intersystems.carrental;

import com.link_intersystems.app.context.ApplicationContext;

public class CarRentalApp {

    public static void main(String[] args) {
        CarRentalApp carRentalApp = new CarRentalApp();
        carRentalApp.run();
    }

    private void run() {
        ApplicationContext applicationContext = new ApplicationContext();
        MainFrame mainFrame = applicationContext.getBean(MainFrame.class);
        mainFrame.show();
    }
}
