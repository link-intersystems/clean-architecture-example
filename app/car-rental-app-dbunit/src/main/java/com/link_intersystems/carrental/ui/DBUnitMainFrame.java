package com.link_intersystems.carrental.ui;

import com.link_intersystems.carrental.offer.CarOfferView;
import com.link_intersystems.swing.DimensionExt;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.*;

public class DBUnitMainFrame {

    public static final String CAR_OFFERS_TAB_TITLE = "Car offers";

    private JFrame mainFrame = new JFrame();
    private JTabbedPane tabbedPane = new JTabbedPane();

    public DBUnitMainFrame() {
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Point centeredFrameLocation = new DimensionExt(mainFrame.getSize()).centerOn(screenSize);
        mainFrame.setLocation(centeredFrameLocation);
        mainFrame.add(tabbedPane, BorderLayout.CENTER);
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
