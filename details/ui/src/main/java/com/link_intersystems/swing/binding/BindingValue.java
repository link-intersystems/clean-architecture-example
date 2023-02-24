package com.link_intersystems.swing.binding;

public interface BindingValue<T> {

    public void setValue(T value);
    public T getValue();
}
