package com.link_intersystems.carrental.swing.binding;

public interface BindingValue<T> {

    public void setValue(T value);

    public T getValue();
}
