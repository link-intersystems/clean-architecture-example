package com.link_intersystems.ioc.declaration;

import java.lang.reflect.Method;

public class BeanFactoryMethodBeanDeclaration extends BeanDeclaration {
    private Method beanFactoryMethod;

    public BeanFactoryMethodBeanDeclaration(Class<?> beanType, Method beanFactoryMethod, BeanConfigBeanDeclarationLocation location) {
        super(beanType, location);
        this.beanFactoryMethod = beanFactoryMethod;
    }

    @Override
    public String getBeanName() {
        return beanFactoryMethod.getName();
    }

    public Method getBeanFactoryMethod() {
        return beanFactoryMethod;
    }

    @Override
    public BeanConfigBeanDeclarationLocation getLocation() {
        return (BeanConfigBeanDeclarationLocation) super.getLocation();
    }
}
