package com.link_intersystems.carrental.swing.notification;

import java.awt.*;

public interface MessageDialog {

    default public void showException(Throwable ex) {
        showException(ex.getClass().getSimpleName(), ex);
    }

    void showException(String title, Throwable ex);

    void showInfo(String info);

    int showDialog(String title, Component content);
}
