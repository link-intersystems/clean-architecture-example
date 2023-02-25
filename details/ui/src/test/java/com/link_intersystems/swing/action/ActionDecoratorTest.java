package com.link_intersystems.swing.action;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import static javax.swing.Action.*;
import static org.junit.jupiter.api.Assertions.*;

class ActionDecoratorTest {

    private AbstractAction decoratedAction;
    private ActionDecorator actionDecorator;

    @BeforeEach
    void setUp() {
        decoratedAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        };

        actionDecorator = new ActionDecorator(decoratedAction);
    }

    @Test
    void decoratorOverridesDecoratedActionProperty() {
        actionDecorator.putValue(NAME, "DECORATED");
        decoratedAction.putValue(NAME, "ORIG");

        assertEquals("DECORATED", actionDecorator.getValue(NAME));
    }

    @Test
    void decoratedActionProperty() {
        decoratedAction.putValue(NAME, "ORIG");

        assertEquals("ORIG", actionDecorator.getValue(NAME));
    }

    @Test
    void actionDecoratorProperty() {
        actionDecorator.putValue(NAME, "DECORATED");

        assertEquals("DECORATED", actionDecorator.getValue(NAME));
    }

    @Test
    void noKeys() {
        Object[] keys = actionDecorator.getKeys();
        assertNull(keys);
    }

    @Test
    void keysFromDecoratedAction() {
        decoratedAction.putValue(NAME, "name");
        assertArrayEquals(new Object[]{NAME}, actionDecorator.getKeys());
    }

    @Test
    void keysFromDecoratedActionIfNotAnAbstractAction() {
        Action decoratedAction = new TestAction();

        decoratedAction.putValue(ACCELERATOR_KEY, "ACCELERATOR_KEY");
        decoratedAction.putValue(ACTION_COMMAND_KEY, "ACTION_COMMAND_KEY");
        decoratedAction.putValue(DEFAULT, "DEFAULT");
        decoratedAction.putValue(DISPLAYED_MNEMONIC_INDEX_KEY, "DISPLAYED_MNEMONIC_INDEX_KEY");
        decoratedAction.putValue(LONG_DESCRIPTION, "LONG_DESCRIPTION");
        decoratedAction.putValue(MNEMONIC_KEY, "MNEMONIC_KEY");
        decoratedAction.putValue(NAME, "NAME");
        decoratedAction.putValue(SELECTED_KEY, "SELECTED_KEY");
        decoratedAction.putValue(SHORT_DESCRIPTION, "SHORT_DESCRIPTION");
        decoratedAction.putValue(SMALL_ICON, "SMALL_ICON");
        decoratedAction.putValue(LARGE_ICON_KEY, "LARGE_ICON_KEY");

        ActionDecorator actionDecorator = new ActionDecorator(decoratedAction);


        assertArrayEquals(new Object[]{
                ACCELERATOR_KEY,
                ACTION_COMMAND_KEY,
                DEFAULT,
                DISPLAYED_MNEMONIC_INDEX_KEY,
                LARGE_ICON_KEY,
                LONG_DESCRIPTION,
                MNEMONIC_KEY,
                NAME,
                SELECTED_KEY,
                SHORT_DESCRIPTION,
                SMALL_ICON,
        }, actionDecorator.getKeys());
    }

    @Test
    void keysFromDecorator() {
        actionDecorator.putValue(NAME, "name");
        assertArrayEquals(new Object[]{NAME}, actionDecorator.getKeys());
    }

    @Test
    void keysFromDecoratorMergedWithDecoratedKeys() {
        decoratedAction.putValue(SHORT_DESCRIPTION, "short desc");
        actionDecorator.putValue(NAME, "name");
        assertArrayEquals(new Object[]{NAME, SHORT_DESCRIPTION}, actionDecorator.getKeys());
    }

    @Test
    void enablementDecoratedAction() {
        decoratedAction.setEnabled(false);
        assertFalse(actionDecorator.isEnabled());

        decoratedAction.setEnabled(true);
        assertTrue(actionDecorator.isEnabled());
    }

    private static class TestAction implements Action {

        private Map<String, Object> values = new HashMap<>();

        @Override
        public Object getValue(String key) {
            return values.get(key);
        }

        @Override
        public void putValue(String key, Object value) {
            values.put(key, value);
        }

        @Override
        public void setEnabled(boolean b) {

        }

        @Override
        public boolean isEnabled() {
            return false;
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener listener) {

        }

        @Override
        public void removePropertyChangeListener(PropertyChangeListener listener) {

        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}