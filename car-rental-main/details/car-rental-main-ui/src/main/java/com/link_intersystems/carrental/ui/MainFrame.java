package com.link_intersystems.carrental.ui;

import com.link_intersystems.carrental.application.ui.ApplicationModel;
import com.link_intersystems.carrental.application.ui.ApplicationView;
import com.link_intersystems.carrental.login.ui.UserModel;
import com.link_intersystems.carrental.management.CarManagementView;
import com.link_intersystems.carrental.offer.ui.CarOfferBookingView;
import com.link_intersystems.carrental.offer.ui.CarOfferView;
import com.link_intersystems.swing.DimensionExt;
import com.link_intersystems.swing.DisplayResolution;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

import static javax.swing.WindowConstants.*;

public class MainFrame implements ApplicationView {

    public static final String CAR_OFFERS_TAB_TITLE = "Car offers";
    public static final String CAR_MANAGEMENT_TAB_TITLE = "Car Management";

    private JFrame mainFrame = new JFrame();
    private JTabbedPane tabbedPane = new JTabbedPane();
    private Optional<ApplicationModel> applicationModel = Optional.empty();
    private Optional<CarManagementView> carManagementView = Optional.empty();
    private Optional<CarOfferBookingView> carOfferBookingView = Optional.empty();

    public MainFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setSize(DisplayResolution.XGA.getDimension());
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        updateTitle();

        Point centeredFrameLocation = new DimensionExt(mainFrame.getSize()).centerOn(screenSize);
        mainFrame.setLocation(centeredFrameLocation);
        mainFrame.add(tabbedPane, BorderLayout.CENTER);
    }

    private void updateTitle() {
        StringBuilder titleBuilder = new StringBuilder("Car Rental App");

        Optional<UserModel> customerModel = applicationModel.map(ApplicationModel::getUserModel);
        customerModel.ifPresent(cm -> {
            titleBuilder.append(" - ");
            titleBuilder.append(cm.getFirstname());
            titleBuilder.append(", ");
            titleBuilder.append(cm.getLastname());
        });

        mainFrame.setTitle(titleBuilder.toString());
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

    public void show() {
        mainFrame.setVisible(true);
    }

    @Override
    public void setModel(ApplicationModel applicationModel) {
        this.applicationModel = Optional.ofNullable(applicationModel);
        updateTitle();
    }
}
