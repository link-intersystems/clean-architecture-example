package com.link_intersystems.ioc.declaration;

import com.link_intersystems.ioc.declaration.location.UnknownBeanDeclarationLocation;

import static java.util.Objects.*;

public class DefaultBeanDeclaration implements BeanDeclaration {

    private Class<?> beanType;
    private BeanDeclarationLocation location;

    public DefaultBeanDeclaration(Class<?> beanType) {
        this(beanType, UnknownBeanDeclarationLocation.INSTANCE);
    }

    public DefaultBeanDeclaration(Class<?> beanType, BeanDeclarationLocation location) {
        this.beanType = requireNonNull(beanType);
        this.location = requireNonNull(location);
    }

    @Override
    public Class<?> getBeanType() {
        return beanType;
    }

    @Override
    public String getBeanName() {
        return getBeanType().getName();
    }

    @Override
    public BeanDeclarationLocation getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return beanType.getName() + " [" + location + ']';
    }
}
