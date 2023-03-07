package com.link_intersystems.carrental.management.pickup.ui;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;
import java.time.LocalDateTime;

public class LocalDateTimePropertyEditor implements PropertyEditor {

    private LocalDateTime localDateTime;

    @Override
    public void setValue(Object value) {
        this.localDateTime = (LocalDateTime) value;
    }

    @Override
    public Object getValue() {
        return localDateTime;
    }

    @Override
    public boolean isPaintable() {
        return false;
    }

    @Override
    public void paintValue(Graphics gfx, Rectangle box) {

    }

    @Override
    public String getJavaInitializationString() {
        return null;
    }

    @Override
    public String getAsText() {
        if (localDateTime != null) {
            return localDateTime.toString();
        }
        return null;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text != null) {
            localDateTime = LocalDateTime.parse(text);
            return;
        }
        this.localDateTime = null;
    }

    @Override
    public String[] getTags() {
        return new String[0];
    }

    @Override
    public Component getCustomEditor() {
        return null;
    }

    @Override
    public boolean supportsCustomEditor() {
        return false;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {

    }
}
