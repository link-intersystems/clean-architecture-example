package com.link_intersystems.carrental.ui;

import com.link_intersystems.ioc.api.LazyBeanSetter;
import com.link_intersystems.carrental.management.CarManagementView;
import com.link_intersystems.carrental.offer.CarOfferView;
import com.link_intersystems.carrental.swing.notification.DefaultMessageDialog;
import com.link_intersystems.carrental.swing.notification.MessageDialog;

public class CarRentalMainUIConfig {

    public CarRentalMainFrame getMainFrame(CarOfferView carOfferView, CarManagementView carManagementView) {
        CarRentalMainFrame carRentalMainFrame = new CarRentalMainFrame();

        carRentalMainFrame.setCarOfferView(carOfferView);
        carRentalMainFrame.setCarManagementView(carManagementView);

        return carRentalMainFrame;
    }

    public MessageDialog getMessageDialog(LazyBeanSetter<CarRentalMainFrame> lazyMainFrameSetter) {
        DefaultMessageDialog defaultMessageDialog = new DefaultMessageDialog();
        lazyMainFrameSetter.setBean(mf -> defaultMessageDialog.setParentComponent(mf.getComponent()));
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                defaultMessageDialog.showException(e);
            }
        });
        return defaultMessageDialog;
    }

}
