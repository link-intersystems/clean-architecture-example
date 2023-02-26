package com.link_intersystems.carrental.ui;

import com.link_intersystems.carrental.MainFrame;
import com.link_intersystems.carrental.MessageDialog;
import com.link_intersystems.carrental.ThrowableView;
import com.link_intersystems.carrental.booking.CarOfferView;

import javax.swing.*;
import java.awt.*;

public class MainConfig {

    public MainFrame getMainFrame(CarOfferView carOfferView) {
        MainFrame mainFrame = new MainFrame();

        mainFrame.setCarOfferView(carOfferView);
        return mainFrame;
    }

    public MessageDialog getMessageDialog() {
        return new MessageDialog() {

            @Override
            public void showException(Throwable ex) {
                ThrowableView throwableView = new ThrowableView();
                throwableView.setException(ex);
                JOptionPane.showMessageDialog(null, throwableView.getViewComponent());
            }

            @Override
            public void showInfo(String info) {
                JOptionPane.showMessageDialog(null, info, "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        };
    }
}
