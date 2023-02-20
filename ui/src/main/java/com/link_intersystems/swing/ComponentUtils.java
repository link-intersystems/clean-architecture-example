package com.link_intersystems.swing;

import java.awt.*;

public class ComponentUtils {

    public static void centerOnScreen(Window window) {
        Dimension windowSize = window.getSize();
        Point centeredLocation = center(windowSize);
        window.setLocation(centeredLocation);
    }

    public static Point center(Dimension toCenter) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        return center(toCenter, dimension);
    }

    public static Point center(Dimension toCenter, Dimension referenceDimension) {
        int centeredX = (int) ((referenceDimension.getWidth() - toCenter.getWidth()) / 2);
        int centeredY = (int) ((referenceDimension.getHeight() - toCenter.getHeight()) / 2);
        return new Point(centeredX, centeredY);
    }
}
