package com.link_intersystems.carrental.swing.binding;

public interface BindingValue<T> {

    public T getValue();

    public void setValue(T value);
}
