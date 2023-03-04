package com.link_intersystems.swing.table;

import java.util.function.BiConsumer;
import java.util.function.Function;

import static java.util.Objects.*;

public class ColumnDescriptor<E> {
    private String name;
    private Function<E, Object> valueGetter;
    private BiConsumer<E, Object> valueSetter;

    public ColumnDescriptor(String name, Function<E, Object> valueGetter) {
        this.name = requireNonNull(name);
        this.valueGetter = requireNonNull(valueGetter);
    }

    public void setValueSetter(BiConsumer<E, Object> valueSetter) {
        this.valueSetter = valueSetter;
    }

    public String getName() {
        return name;
    }

    public Function<E, Object> getValueGetter() {
        return valueGetter;
    }

    public BiConsumer<E, Object> getValueSetter() {
        return valueSetter;
    }
}