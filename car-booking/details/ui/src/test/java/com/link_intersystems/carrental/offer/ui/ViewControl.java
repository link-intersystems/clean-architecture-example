package com.link_intersystems.carrental.offer.ui;

import org.junit.jupiter.api.Assertions;

import javax.swing.*;
import java.awt.*;
import java.util.function.Predicate;

public class ViewControl {

    private Component viewComponent;

    public ViewControl(Component viewComponent) {
        this.viewComponent = viewComponent;
    }

    public void clickButton(String text) {
        AbstractButton button = findComponent(AbstractButton.class, b -> text.equals(b.getText()));
        if (button == null) {
            Assertions.fail("No button named " + text + " found in " + viewComponent);
        }
        button.doClick();
    }

    public <T> T findComponent(Class<T> childComponentType) {
        return findComponent(childComponentType, c -> true);
    }

    public <T> T findComponent(Class<T> childComponentType, Predicate<T> filter) {
        return findComponent(viewComponent, childComponentType, filter);
    }

    private <T> T findComponent(Component parent, Class<T> childComponentType, Predicate<T> filter) {
        if (parent instanceof Container container) {
            Component[] components = container.getComponents();
            for (Component component : components) {
                if (childComponentType.isInstance(component)) {
                    T typeMatchedComponent = childComponentType.cast(component);
                    if (filter.test(typeMatchedComponent)) {
                        return typeMatchedComponent;
                    }
                }
                T result = findComponent(component, childComponentType, filter);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

}
