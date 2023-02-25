package com.link_intersystems.carrental.ui;

import com.link_intersystems.carrental.MainFrame;
import com.link_intersystems.carrental.MessageDialog;
import com.link_intersystems.carrental.ThrowableView;
import com.link_intersystems.carrental.booking.CarOfferView;
import com.link_intersystems.plugins.AbstractServiceProvider;
import com.link_intersystems.plugins.ApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.util.Set;
import java.util.function.BiConsumer;

public class MainServiceProvider extends AbstractServiceProvider {


    @Override
    protected void doInit(ApplicationContext applicationContext, BiConsumer<Class<?>, Object> registerService) {
        MainFrame mainFrame = new MainFrame();

        registerService.accept(MessageDialog.class, getMessageDialog(mainFrame.getComponent()));

        CarOfferView carOfferView = applicationContext.getService(CarOfferView.class);
        mainFrame.setCarOfferView(carOfferView);

        registerService.accept(MainFrame.class, mainFrame);
    }

    @Override
    protected void initProvidedServiceType(Set<Class<?>> providedServices) {
        providedServices.add(MainFrame.class);
        providedServices.add(MessageDialog.class);
    }

    public MessageDialog getMessageDialog(Component parentComponent) {
        return new MessageDialog() {
            @Override
            public void showException(Throwable ex) {
                ThrowableView throwableView = new ThrowableView();
                throwableView.setException(ex);
                JOptionPane.showMessageDialog(parentComponent, throwableView.getViewComponent());
            }

            @Override
            public void showInfo(String info) {
                JOptionPane.showMessageDialog(parentComponent, info, "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        };
    }
}
