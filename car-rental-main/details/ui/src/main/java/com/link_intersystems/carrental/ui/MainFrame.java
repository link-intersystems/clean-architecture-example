package com.link_intersystems.carrental.ui;

import com.link_intersystems.carrental.login.ui.UserModel;
import com.link_intersystems.swing.DimensionExt;
import com.link_intersystems.swing.DisplayResolution;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static javax.swing.WindowConstants.*;

public class MainFrame implements ApplicationView {

    private JFrame mainFrame = new JFrame();
    private JTabbedPane tabbedPane = new JTabbedPane();
    private Optional<ApplicationModel> applicationModel = Optional.empty();
    private List<View> views = new ArrayList<>();

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

    public void addView(View view) {
        applicationModel.ifPresent(m -> addView(m, view));
    }

    private void addView(ApplicationModel applicationModel, View view) {
        UserModel userModel = applicationModel.getUserModel();

        List<String> requiredRoles = view.getRequiredRoles();
        if (userModel.isAllowed(requiredRoles)) {
            String title = view.getTitle();
            removeTab(title);

            Component viewComponent = view.createComponent(applicationModel);
            tabbedPane.addTab(title, viewComponent);
        }
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
        views.forEach(this::addView);

        mainFrame.setVisible(true);
    }

    @Override
    public void setModel(ApplicationModel applicationModel) {
        this.applicationModel = Optional.ofNullable(applicationModel);
        updateTitle();
    }

    public void setViews(List<View> views) {
        this.views = new ArrayList<>(views);
    }
}
