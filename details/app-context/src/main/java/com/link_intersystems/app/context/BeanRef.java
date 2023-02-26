package com.link_intersystems.app.context;

import java.util.Objects;

class BeanRef {
    private Class<?> type;
    private String name;


    public BeanRef(Class<?> type, String name) {
        this.type = type;
        this.name = name;
    }

    public Class<?> getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeanRef beanRef = (BeanRef) o;
        return Objects.equals(type, beanRef.type) && Objects.equals(name, beanRef.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(type.getName());
        if (name != null) {
            sb.append(" (");
            sb.append(name);
            sb.append(")");
        }
        return sb.toString();
    }
}
