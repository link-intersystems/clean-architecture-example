package com.link_intersystems.carrental.ui;

import com.link_intersystems.app.context.BeanFactory;
import com.link_intersystems.app.context.LazyBeanSetter;
import com.link_intersystems.carrental.MainFrame;
import com.link_intersystems.carrental.MessageDialog;
import com.link_intersystems.carrental.booking.CarOfferView;

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
