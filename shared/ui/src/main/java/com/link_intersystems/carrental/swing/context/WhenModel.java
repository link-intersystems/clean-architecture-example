package com.link_intersystems.carrental.swing.context;

public interface WhenModel<T> {
    ModelAction<T> removed();

    ModelAction<T> added();

    ModelAction<T> changed();

    void dispose();
}
