package com.link_intersystems.ioc.declaration;

import static java.util.Objects.*;

public class BeanDeclaration {

    private Class<?> beanType;
    private BeanDeclarationLocation location;

    public BeanDeclaration(Class<?> beanType, BeanDeclarationLocation location) {
        this.beanType = requireNonNull(beanType);
        this.location = requireNonNull(location);
    }

    public Class<?> getBeanType() {
        return beanType;
    }

    public String getBeanName() {
        return getBeanType().getName();
    }

    public BeanDeclarationLocation getLocation() {
        return location;
    }
}
