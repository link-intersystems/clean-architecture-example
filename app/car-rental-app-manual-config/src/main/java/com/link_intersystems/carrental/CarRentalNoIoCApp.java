package com.link_intersystems.carrental;

import com.link_intersystems.carrental.ui.CarRentalMainFrame;

public class CarRentalNoIoCApp {

    public static void main(String[] args) {
        CarRentalNoIoCApp carRentalApp = new CarRentalNoIoCApp();
        carRentalApp.run(args);
    }

    void run(String[] args) {
        CarRentalMain carRentalMain = new CarRentalMain();
        CarRentalMainFrame mainFrame = carRentalMain.createMainFrame();
        openFrame(mainFrame);
    }

    protected void openFrame(CarRentalMainFrame mainFrame) {
        mainFrame.show();
    }

}
