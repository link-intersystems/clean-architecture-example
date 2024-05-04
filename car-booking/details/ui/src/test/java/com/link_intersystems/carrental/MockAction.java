package com.link_intersystems.carrental;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MockAction extends AbstractAction {

    private boolean performed = false;

    public MockAction(String name) {
        super(name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        performed = true;
    }

    public void assertPerformed() {
        assertTrue(performed, getValue(Action.NAME) + " not performed");
    }
}