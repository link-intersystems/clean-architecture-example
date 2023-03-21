package com.link_intersystems.ioc.declaration;

public interface BeanDeclaration {
    Class<?> getBeanType();

    String getBeanName();

    BeanDeclarationLocation getLocation();

    @Override
    String toString();
}
