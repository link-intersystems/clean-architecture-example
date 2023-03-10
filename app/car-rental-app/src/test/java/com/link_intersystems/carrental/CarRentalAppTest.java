package com.link_intersystems.carrental;

import com.link_intersystems.carrental.ui.CarRentalMainFrame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;

class CarRentalAppTest {

    @Test
    void applicationContext(@TempDir File tempDir) {
        CarRentalApp carRentalApp = new CarRentalApp() {
            @Override
            protected void openFrame(CarRentalMainFrame mainFrame) {
            }
        };


        carRentalApp.run(new String[]{"-db", tempDir.getAbsolutePath()});
    }
}