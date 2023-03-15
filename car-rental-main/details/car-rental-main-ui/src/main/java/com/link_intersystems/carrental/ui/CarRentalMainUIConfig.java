package com.link_intersystems.carrental.ui;

import com.link_intersystems.carrental.management.CarManagementView;
import com.link_intersystems.carrental.offer.CarOfferView;
import com.link_intersystems.carrental.swing.notification.DefaultMessageDialog;
import com.link_intersystems.carrental.swing.notification.MessageDialog;
import com.link_intersystems.ioc.api.LazyBeanSetter;

public class CarRentalMainUIConfig {

    public CarRentalMainFrame getMainFrame(CarOfferView carOfferView, CarManagementView carManagementView) {
        CarRentalMainFrame carRentalMainFrame = new CarRentalMainFrame();

        carRentalMainFrame.setCarOfferView(carOfferView);
        carRentalMainFrame.setCarManagementView(carManagementView);

        return carRentalMainFrame;
    }

    public Void initMainFrame(CarRentalMainFrame carRentalMainFrame, MessageDialog messageDialog) {

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                messageDialog.showException(e);
            }
        });

        return null;
    }

    public MessageDialog getMessageDialog(LazyBeanSetter<CarRentalMainFrame> lazyMainFrameSetter) {
        DefaultMessageDialog defaultMessageDialog = new DefaultMessageDialog();
        lazyMainFrameSetter.setBean(mf -> defaultMessageDialog.setParentComponent(mf.getComponent()));
        return defaultMessageDialog;
    }

}
