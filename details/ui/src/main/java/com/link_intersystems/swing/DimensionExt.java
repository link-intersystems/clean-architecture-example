package com.link_intersystems.swing;

import java.awt.*;

public class DimensionExt extends Dimension {

    public DimensionExt() {
    }

    public DimensionExt(Dimension d) {
        super(d);
    }

    public DimensionExt(int width, int height) {
        super(width, height);
    }

    public Point centerOn(Dimension referenceDimension) {
        return centerOn(referenceDimension.width, referenceDimension.height);
    }

    public Point centerOn(int width, int height) {
        return centerOn(new DimensionExt(width, height));
    }

    public Point centerOn(DimensionExt referenceDimension) {
        Point centerLocation = getCenterLocation();
        Point referenceCenterLocation = referenceDimension.getCenterLocation();

        int centeredX = referenceCenterLocation.x - centerLocation.x;
        int centeredY = referenceCenterLocation.y - centerLocation.y;

        return new Point(centeredX, centeredY);
    }


    public Point getCenterLocation() {
        int centeredX = (int) ((getWidth()) / 2);
        int centeredY = (int) ((getHeight()) / 2);

        return new Point(centeredX, centeredY);
    }
}
