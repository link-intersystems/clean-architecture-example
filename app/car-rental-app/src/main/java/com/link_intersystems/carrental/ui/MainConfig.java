package com.link_intersystems.carrental.ui;

import com.link_intersystems.app.context.BeanFactory;
import com.link_intersystems.app.context.LazyBeanSetter;
import com.link_intersystems.carrental.management.CarManagementView;
import com.link_intersystems.carrental.offer.CarOfferView;
import com.link_intersystems.carrental.swing.notification.MessageDialog;
import com.link_intersystems.carrental.swing.notification.DefaultMessageDialog;

public class MainConfig {

    private BeanFactory beanFactory;

    public MainConfig(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public MainFrame getMainFrame(CarOfferView carOfferView, CarManagementView carManagementView) {
        MainFrame mainFrame = new MainFrame();

        mainFrame.setCarOfferView(carOfferView);
        mainFrame.setCarManagementView(carManagementView);

        return mainFrame;
    }

    public MessageDialog getMessageDialog(LazyBeanSetter<MainFrame> lazyMainFrameSetter) {
        DefaultMessageDialog defaultMessageDialog = new DefaultMessageDialog();
        lazyMainFrameSetter.setBean(mf -> defaultMessageDialog.setParentComponent(mf.getComponent()));
        return defaultMessageDialog;
    }

}
