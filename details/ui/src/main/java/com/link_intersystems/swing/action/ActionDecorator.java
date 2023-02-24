package com.link_intersystems.swing.action;

import com.link_intersystems.events.beans.PropertyChangeMethod;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

public class ActionDecorator extends AbstractAction {

    private PropertyChangeListener propertyChangeListener = PropertyChangeMethod.CHANGE.listener(this::onDecoratedActionChange);

    private Map<String, Object> decoratedActionPropertyValues = new HashMap<>();

    private Action decoratedAction;

    public ActionDecorator() {
    }

    public ActionDecorator(Action decoratedAction) {
        setDecoratedAction(decoratedAction);
    }

    public void setDecoratedAction(Action decoratedAction) {
        if (this.decoratedAction != null) {
            this.decoratedAction.removePropertyChangeListener(propertyChangeListener);
        }

        this.decoratedAction = decoratedAction;

        if (this.decoratedAction != null) {
            this.decoratedAction.addPropertyChangeListener(propertyChangeListener);
        }
    }

    private void onDecoratedActionChange(PropertyChangeEvent ce) {
        String propertyName = ce.getPropertyName();

        Object thisOldValue = getValue(propertyName);

        Object newValue = ce.getNewValue();
        if ("enabled".equals(propertyName)) {
            setEnabled((Boolean) newValue);
        } else {
            decoratedActionPropertyValues.put(propertyName, newValue);
        }

        Object thisNewValue = getValue(propertyName);
        firePropertyChange(propertyName, thisOldValue, thisNewValue);
    }

    @Override
    public Object getValue(String key) {
        Object[] keys = super.getKeys();
        if (keys != null && Arrays.asList(keys).contains(key)) {
            return super.getValue(key);
        } else {
            return decoratedActionPropertyValues.get(key);
        }
    }

    @Override
    public Object[] getKeys() {
        Object[] thisKeys = super.getKeys();
        Object[] decoratedActionKeys = null;
        if (decoratedAction != null) {
            ActionKeys actionKeys;
            if (decoratedAction instanceof AbstractAction) {
                AbstractAction abstractDecoratedAction = (AbstractAction) decoratedAction;
                actionKeys = new AbstractActionActionKeys(abstractDecoratedAction);
            } else {
                actionKeys = new GeneralActionActionKeys(decoratedAction);
            }

            decoratedActionKeys = actionKeys.getKeys();
        }

        if (thisKeys == null) {
            return decoratedActionKeys;
        }

        if (decoratedActionKeys == null) {
            return thisKeys;
        }

        return mergeKeys(thisKeys, decoratedActionKeys);
    }

    private Object[] mergeKeys(Object[] thisKeys, Object[] decoratedActionKeys) {
        LinkedHashSet<Object> mergedKeys = new LinkedHashSet<>(Arrays.asList(thisKeys));
        mergedKeys.addAll(new LinkedHashSet<>(Arrays.asList(decoratedActionKeys)));
        return mergedKeys.toArray();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (decoratedAction != null) {
            decoratedAction.actionPerformed(e);
        }
    }


    private static interface ActionKeys {

        public Object[] getKeys();
    }

    private static class AbstractActionActionKeys implements ActionKeys {

        private AbstractAction abstractAction;

        public AbstractActionActionKeys(AbstractAction abstractAction) {
            this.abstractAction = abstractAction;
        }

        @Override
        public Object[] getKeys() {
            return abstractAction.getKeys();
        }
    }

    private static class GeneralActionActionKeys implements ActionKeys {

        private Action action;

        public GeneralActionActionKeys(Action action) {
            this.action = action;
        }

        @Override
        public Object[] getKeys() {
            List<Object> keys = new ArrayList<>();

            addValue(keys, ACCELERATOR_KEY);
            addValue(keys, ACTION_COMMAND_KEY);
            addValue(keys, DEFAULT);
            addValue(keys, DISPLAYED_MNEMONIC_INDEX_KEY);
            addValue(keys, LARGE_ICON_KEY);
            addValue(keys, LONG_DESCRIPTION);
            addValue(keys, MNEMONIC_KEY);
            addValue(keys, NAME);
            addValue(keys, SELECTED_KEY);
            addValue(keys, SHORT_DESCRIPTION);
            addValue(keys, SMALL_ICON);

            return keys.toArray();
        }

        private void addValue(List<Object> keys, String propertyName) {
            Object value = action.getValue(propertyName);

            if (value == null) {
                return;
            }

            keys.add(propertyName);
        }
    }
}
