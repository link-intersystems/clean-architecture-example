package com.link_intersystems.app.context;

import java.util.Objects;

public class BeanId {

    private Class<?> type;
    private String name;

    BeanId(Class<?> type, String name) {
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
        BeanId beanDeclaration = (BeanId) o;
        return Objects.equals(type, beanDeclaration.type) && Objects.equals(name, beanDeclaration.name);
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
