package com.link_intersystems.carrental.ui;

import com.link_intersystems.app.context.BeanFactory;
import com.link_intersystems.carrental.MainFrame;
import com.link_intersystems.carrental.MessageDialog;
import com.link_intersystems.carrental.ThrowableView;
import com.link_intersystems.carrental.booking.CarOfferView;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

public class MainConfig {


    private BeanFactory beanFactory;

    public MainConfig(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public MainFrame getMainFrame(CarOfferView carOfferView) {
        MainFrame mainFrame = new MainFrame();

        mainFrame.setCarOfferView(carOfferView);
        return mainFrame;
    }

    public MessageDialog getMessageDialog(Supplier<MainFrame> mainFrameSupplier) {
        return new MessageDialog() {

            @Override
            public void showException(Throwable ex) {
                ThrowableView throwableView = new ThrowableView();
                throwableView.setException(ex);
                JOptionPane.showMessageDialog(getParentComponent(), throwableView.getViewComponent());
            }

            private Component getParentComponent() {
                return mainFrameSupplier.get().getComponent();
            }

            @Override
            public void showInfo(String info) {
                JOptionPane.showMessageDialog(getParentComponent(), info, "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        };
    }
}
