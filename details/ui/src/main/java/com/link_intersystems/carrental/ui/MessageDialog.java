package com.link_intersystems.carrental.ui;

public interface MessageDialog {
    void showException(Throwable ex);

    void showInfo(String info);
}
