package com.link_intersystems.carrental.main;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;

class CarRentalAppIntTest {

    @Test
    void applicationContext(@TempDir File tempDir) {
        CarRentalApp app = new CarRentalApp() {
        };


        app.run(new String[]{"-db", tempDir.getAbsolutePath()});
    }
}