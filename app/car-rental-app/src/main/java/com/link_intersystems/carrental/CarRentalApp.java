package com.link_intersystems.carrental;

import com.link_intersystems.carrental.ui.CarRentalMainFrame;

public class CarRentalApp {

    public static void main(String[] args) {
        CarRentalApp carRentalApp = new CarRentalApp();
        carRentalApp.run(args);
    }

    void run(String[] args) {
        CarRentalMain carRentalMain = new CarRentalMain();
        carRentalMain.initDataSource();
        CarRentalMainFrame mainFrame = carRentalMain.createMainFrame();
        openFrame(mainFrame);
    }

    protected void openFrame(CarRentalMainFrame mainFrame) {
        mainFrame.show();
    }

}
