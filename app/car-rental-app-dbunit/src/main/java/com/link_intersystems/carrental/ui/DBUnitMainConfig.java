package com.link_intersystems.carrental.ui;

import com.link_intersystems.ioc.BeanFactory;
import com.link_intersystems.ioc.LazyBeanSetter;
import com.link_intersystems.carrental.offer.CarOfferView;
import com.link_intersystems.carrental.swing.notification.DefaultMessageDialog;
import com.link_intersystems.carrental.swing.notification.MessageDialog;

public class DBUnitMainConfig {

    private BeanFactory beanFactory;

    public DBUnitMainConfig(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public DBUnitMainFrame getMainFrame(CarOfferView carOfferView) {
        DBUnitMainFrame DBUnitMainFrame = new DBUnitMainFrame();

        DBUnitMainFrame.setCarOfferView(carOfferView);

        return DBUnitMainFrame;
    }

    public MessageDialog getMessageDialog(LazyBeanSetter<DBUnitMainFrame> lazyMainFrameSetter) {
        DefaultMessageDialog defaultMessageDialog = new DefaultMessageDialog();
        lazyMainFrameSetter.setBean(mf -> defaultMessageDialog.setParentComponent(mf.getComponent()));
        return defaultMessageDialog;
    }

}
