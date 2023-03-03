package com.link_intersystems.swing.notification;

import com.link_intersystems.swing.exception.ThrowableView;

import javax.swing.*;
import java.awt.*;

public class OptionPaneMessageDialog implements MessageDialog {

    private Component parentComponent;

    public void setParentComponent(Component parentComponent) {
        this.parentComponent = parentComponent;
    }

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
}
