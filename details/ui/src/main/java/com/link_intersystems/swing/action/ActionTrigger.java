package com.link_intersystems.swing.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.event.ActionEvent.*;
import static java.util.Objects.*;

public class ActionTrigger {

    private Object triggerObject;

    public ActionTrigger(Object triggerObject) {
        this.triggerObject = requireNonNull(triggerObject);
    }

    public void performAction(ActionListener actionListener) {
        performAction(actionListener, "");
    }

    public void performAction(ActionListener actionListener, String command) {
        actionListener.actionPerformed(new ActionEvent(triggerObject, ACTION_PERFORMED, command));
    }
}