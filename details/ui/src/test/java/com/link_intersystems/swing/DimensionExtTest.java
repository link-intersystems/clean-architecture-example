package com.link_intersystems.swing;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class DimensionExtTest {

    @Test
    void centerOnWithZeroSizeDimension() {
        DimensionExt dimensionExt = new DimensionExt();
        Point centeredLocation = dimensionExt.centerOn(new Dimension(1280, 1080));

        assertEquals(new Point(640, 540), centeredLocation);
    }


    @Test
    void centerOn() {
        DimensionExt dimensionExt = new DimensionExt(new Dimension(800, 600));
        Point centeredLocation = dimensionExt.centerOn(new Dimension(1280, 1080));

        assertEquals(new Point(240, 240), centeredLocation);
    }

    @Test
    void getCenterLocation() {
        DimensionExt dimensionExt = new DimensionExt(new Dimension(800, 600));

        assertEquals(new Point(400, 300), dimensionExt.getCenterLocation());
    }

    @Test
    void getCenterLocationWithZeroSizeDimension() {
        DimensionExt dimensionExt = new DimensionExt();

        assertEquals(new Point(), dimensionExt.getCenterLocation());
    }

}