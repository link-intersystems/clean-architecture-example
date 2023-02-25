package com.link_intersystems.carrental;

public interface MessageDialog {
    void showException(Throwable ex);

    void showInfo(String info);
}
