package com.link_intersystems.carrental.swing.context;

import java.util.function.Consumer;

public interface ModelAction<T> {
    ModelState then(Consumer<T> modelSetter);
    void then(Runnable runnable);

    void dispose();
}
