package com.link_intersystems.ioc;

import java.net.URL;
import java.util.Objects;

public class BeanDeclaration {

    private URL resource;
    private BeanId beanId;

    BeanDeclaration(URL resource, BeanId beanId) {
        this.resource = resource;
        this.beanId = beanId;
    }

    public URL getResource() {
        return resource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeanDeclaration that = (BeanDeclaration) o;
        return Objects.equals(resource, that.resource) && Objects.equals(beanId, that.beanId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resource, beanId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(beanId.getType().getName());
        if (beanId.getName() != null) {
            sb.append(" (");
            sb.append(beanId.getName());
            sb.append(")");
        }
        return sb.toString();
    }


    public BeanId getId() {
        return beanId;
    }
}
