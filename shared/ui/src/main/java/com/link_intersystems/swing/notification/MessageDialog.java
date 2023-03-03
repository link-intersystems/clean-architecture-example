package com.link_intersystems.swing.notification;

public interface MessageDialog {
    void showException(Throwable ex);

    void showInfo(String info);
}
