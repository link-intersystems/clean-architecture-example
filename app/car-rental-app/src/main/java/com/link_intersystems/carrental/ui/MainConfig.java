package com.link_intersystems.carrental.ui;

import com.link_intersystems.app.context.BeanFactory;
import com.link_intersystems.app.context.LazyBeanSetter;
import com.link_intersystems.carrental.booking.CarOfferView;
import com.link_intersystems.swing.notification.OptionPaneMessageDialog;
import com.link_intersystems.swing.notification.MessageDialog;

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
        OptionPaneMessageDialog optionPaneMessageDialog = new OptionPaneMessageDialog();
        lazyMainFrameSetter.setBean(mf -> optionPaneMessageDialog.setParentComponent(mf.getComponent()));
        return optionPaneMessageDialog;
    }

}
