package com.link_intersystems.carrental.ui;

import com.link_intersystems.app.context.BeanFactory;
import com.link_intersystems.app.context.LazyBeanSetter;
import com.link_intersystems.carrental.management.CarManagementView;
import com.link_intersystems.carrental.offer.CarOfferView;
import com.link_intersystems.swing.notification.MessageDialog;
import com.link_intersystems.swing.notification.OptionPaneMessageDialog;

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
        OptionPaneMessageDialog optionPaneMessageDialog = new OptionPaneMessageDialog();
        lazyMainFrameSetter.setBean(mf -> optionPaneMessageDialog.setParentComponent(mf.getComponent()));
        return optionPaneMessageDialog;
    }

}
