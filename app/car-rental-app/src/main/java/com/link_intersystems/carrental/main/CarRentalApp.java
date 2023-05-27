package com.link_intersystems.carrental.main;

public class CarRentalApp {

    public static void main(String[] args) {
        CarRentalApp carRentalApp = new CarRentalApp();
        carRentalApp.run(args);
    }

    void run(String[] args) {
        AppArgs appArgs = new AppArgs(args);
        BootstrapController bootstrapController = new BootstrapController();
        bootstrapController.execute(appArgs);
    }

}
