package com.link_intersystems.carrental.ui;

import com.link_intersystems.carrental.management.CarManagementView;
import com.link_intersystems.carrental.offer.CarOfferView;
import com.link_intersystems.swing.DimensionExt;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.*;

public class CarRentalMainFrame {

    public static final String CAR_OFFERS_TAB_TITLE = "Car offers";
    public static final String CAR_MANAGEMENT_TAB_TITLE = "Car Management";

    private static final Dimension VGA = new Dimension(640, 480);
    private static final Dimension SVGA = new Dimension(800, 600);
    private static final Dimension XGA = new Dimension(1024, 768);
    private static final Dimension SXGA = new Dimension(1280, 1024);
    private static final Dimension FHD = new Dimension(1920, 1080);

    private JFrame mainFrame = new JFrame();
    private JTabbedPane tabbedPane = new JTabbedPane();

    public CarRentalMainFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setSize(XGA);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Point centeredFrameLocation = new DimensionExt(mainFrame.getSize()).centerOn(screenSize);
        mainFrame.setLocation(centeredFrameLocation);
        mainFrame.add(tabbedPane, BorderLayout.CENTER);
    }

    public void setCarManagementView(CarManagementView carManagementView) {
        removeTab(CAR_MANAGEMENT_TAB_TITLE);

        Component viewComponent = carManagementView.getViewComponent();
        tabbedPane.addTab(CAR_MANAGEMENT_TAB_TITLE, viewComponent);
    }

    public void setCarOfferView(CarOfferView carOfferView) {
        removeTab(CAR_OFFERS_TAB_TITLE);

        Component viewComponent = carOfferView.getViewComponent();
        tabbedPane.addTab(CAR_OFFERS_TAB_TITLE, viewComponent);
    }

    private void removeTab(String title) {
        for (int i = tabbedPane.getTabCount() - 1; i > 0; i--) {
            String titleAt = tabbedPane.getTitleAt(i);
            if (title.equals(titleAt)) {
                tabbedPane.removeTabAt(i);
            }
        }
    }

    public Component getComponent() {
        return mainFrame;
    }

    public void show() {
        mainFrame.setVisible(true);
    }
}
