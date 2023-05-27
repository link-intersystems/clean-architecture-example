package com.link_intersystems.carrental.swing.bean;

public interface Change {
    void onChange(Object beanObject, String propertyName);

    void dispose();
}
