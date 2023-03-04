package com.link_intersystems.carrental.swing.notification;

public interface MessageDialog {
    void showException(Throwable ex);

    void showInfo(String info);
}
