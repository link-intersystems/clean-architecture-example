package com.link_intersystems.carrental;

import com.link_intersystems.carrental.ui.CarRentalMainFrame;
import org.junit.jupiter.api.Test;

class CarRentalAppTest {

    @Test
    void applicationContext() {
        CarRentalApp carRentalApp = new CarRentalApp() {
            @Override
            protected void openFrame(CarRentalMainFrame mainFrame) {
            }
        };

        carRentalApp.run(new String[0]);
    }
}