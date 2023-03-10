package com.link_intersystems.carrental.swing.notification;

import com.link_intersystems.carrental.swing.exception.ThrowableView;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JOptionPane.*;

public class DefaultMessageDialog implements MessageDialog {

    private Component parentComponent;

    public void setParentComponent(Component parentComponent) {
        this.parentComponent = parentComponent;
    }

    @Override
    public void showException(String title, Throwable ex) {
        ThrowableView throwableView = new ThrowableView();
        throwableView.setException(ex);
        JOptionPane.showMessageDialog(parentComponent, throwableView.getViewComponent(), title, ERROR_MESSAGE);
    }

    @Override
    public void showInfo(String info) {
        JOptionPane.showMessageDialog(parentComponent, info, "Info", INFORMATION_MESSAGE);
    }

    @Override
    public int showDialog(String title, Component content) {
        return JOptionPane.showConfirmDialog(parentComponent, content, title, JOptionPane.OK_CANCEL_OPTION);
    }
}
