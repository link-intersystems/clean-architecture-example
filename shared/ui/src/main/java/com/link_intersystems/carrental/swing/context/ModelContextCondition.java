package com.link_intersystems.carrental.swing.context;

import java.util.function.Consumer;

public interface ModelContextCondition<T> {
    SetModelAction thenSet(Consumer<T> modelSetter);

    public void dispose();
}
