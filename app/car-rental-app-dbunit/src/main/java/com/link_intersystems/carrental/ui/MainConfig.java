package com.link_intersystems.carrental.ui;

import com.link_intersystems.app.context.BeanFactory;
import com.link_intersystems.app.context.LazyBeanSetter;
import com.link_intersystems.carrental.offer.CarOfferView;
import com.link_intersystems.carrental.swing.notification.DefaultMessageDialog;
import com.link_intersystems.carrental.swing.notification.MessageDialog;

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

    public MessageDialog getMessageDialog(LazyBeanSetter<MainFrame> lazyMainFrameSetter) {
        DefaultMessageDialog defaultMessageDialog = new DefaultMessageDialog();
        lazyMainFrameSetter.setBean(mf -> defaultMessageDialog.setParentComponent(mf.getComponent()));
        return defaultMessageDialog;
    }

}
