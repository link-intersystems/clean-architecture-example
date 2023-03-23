package com.link_intersystems.carrental;

import com.link_intersystems.carrental.ui.CarRentalMainFrame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;

class CarRentalNoIoCAppTest {

    @Test
    void applicationContext(@TempDir File tempDir) {
        CarRentalNoIoCApp app = new CarRentalNoIoCApp() {

            @Override
            protected void openFrame(CarRentalMainFrame mainFrame) {
            }
        };


        app.run(new String[]{"-db", tempDir.getAbsolutePath()});
    }
}