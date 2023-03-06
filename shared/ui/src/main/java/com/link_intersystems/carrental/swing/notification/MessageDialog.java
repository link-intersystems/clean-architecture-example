package com.link_intersystems.carrental.swing.notification;

import java.awt.*;

public interface MessageDialog {
    void showException(Throwable ex);

    void showInfo(String info);

    int showDialog(String title, Component content);
}
