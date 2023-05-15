package com.link_intersystems.carrental.swing.context;

import java.util.Objects;

import static java.util.Objects.*;

public class ModelQualifier<T> {

    private Class<T> type;
    private String name;

    public ModelQualifier(Class<T> type) {
        this(type, null);
    }

    public ModelQualifier(Class<T> type, String name) {
        this.type = requireNonNull(type);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Class<T> getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelQualifier<?> that = (ModelQualifier<?>) o;
        return Objects.equals(getType(), that.getType()) && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getName());
    }

    @Override
    public String toString() {
        return "ModelQualifier{" +
                getType().getName() +
                "<" + getName() + '>' +
                '}';
    }
}
