package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.swing.notification.MessageDialog;

public class MessageDialogMock implements MessageDialog {

    private String info;
    private Throwable exception;

    public Throwable getException() {
        return exception;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public void showException(Throwable ex) {
        exception = ex;
    }

    @Override
    public void showInfo(String info) {
        this.info = info;
    }
}
