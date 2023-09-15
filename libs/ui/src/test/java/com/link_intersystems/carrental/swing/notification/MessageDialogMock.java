package com.link_intersystems.carrental.swing.notification;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MessageDialogMock implements MessageDialog {

    private Map<String, Supplier<Integer>> showDialogResults = new HashMap<>();

    @Override
    public void showException(String title, Throwable ex) {

    }

    @Override
    public void showInfo(String info) {

    }

    @Override
    public int showDialog(String title, Component content) {
        return showDialogResults.getOrDefault(title, () -> JOptionPane.CANCEL_OPTION).get();
    }

    public ShowDialogInvocation whenShowDialog(String title) {
        return dialogResult -> {
            showDialogResults.put(title, dialogResult);
        };
    }

    public static interface ShowDialogInvocation {
        public void thenReturn(Supplier<Integer> dialogResult);
    }
}