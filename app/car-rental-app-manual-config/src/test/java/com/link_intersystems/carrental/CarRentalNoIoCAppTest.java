package com.link_intersystems.carrental;

import com.link_intersystems.carrental.ui.CarRentalMainFrame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;

class CarRentalNoIoCAppTest {

    @Test
    void run(@TempDir File tempDir) {
        CarRentalNoIoCApp carRentalApp = new CarRentalNoIoCApp() {
            @Override
            protected void openFrame(CarRentalMainFrame mainFrame) {
            }
        };


        carRentalApp.run(new String[]{"-db", tempDir.getAbsolutePath()});
    }
}