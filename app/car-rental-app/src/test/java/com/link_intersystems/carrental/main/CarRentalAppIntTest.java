package com.link_intersystems.carrental.main;

import com.link_intersystems.carrental.ui.CarRentalMainFrame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;

class CarRentalAppIntTest {

    @Test
    void applicationContext(@TempDir File tempDir) {
        JdbcCarRentalApp app = new JdbcCarRentalApp() {

            @Override
            protected void openFrame(CarRentalMainFrame mainFrame) {
            }
        };


        app.run(new String[]{"-db", tempDir.getAbsolutePath()});
    }
}