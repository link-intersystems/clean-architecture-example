package com.link_intersystems.carrental.swing.binding;

public class DefaultBindingValue<T> implements BindingValue<T> {

    private T value;

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
    }
}

