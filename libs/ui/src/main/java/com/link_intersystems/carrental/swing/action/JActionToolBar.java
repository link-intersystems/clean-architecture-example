package com.link_intersystems.carrental.swing.action;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.Objects;

import static com.link_intersystems.carrental.swing.action.ActionConstants.*;

public class JActionToolBar extends JToolBar {

    private ListDataListener actionsListener = new ListDataListener() {
        @Override
        public void intervalAdded(ListDataEvent e) {
            updateActions();
        }

        @Override
        public void intervalRemoved(ListDataEvent e) {
            updateActions();
        }

        @Override
        public void contentsChanged(ListDataEvent e) {
            updateActions();
        }
    };

    private ListModel<Action> toolbarActions = new DefaultListModel<>();


    public void setModel(ListModel<Action> actionModel) {
        this.toolbarActions.removeListDataListener(actionsListener);

        this.toolbarActions = actionModel == null ? new DefaultListModel<>() : actionModel;

        this.toolbarActions.addListDataListener(actionsListener);

        updateActions();
    }

    private void updateActions() {
        removeAll();

        Object latestActionGroup = null;

        for (int i = 0; i < toolbarActions.getSize(); i++) {
            Action elementAt = toolbarActions.getElementAt(i);
            Object actualActionGroup = elementAt.getValue(ACTION_GROUP_KEY);
            if (!Objects.equals(actualActionGroup, latestActionGroup)) {
                addSeparator();
            }
            add(elementAt);
            latestActionGroup = actualActionGroup;
        }

        revalidate();
        repaint();
    }

}
