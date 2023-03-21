package com.link_intersystems.ioc.declaration;

import com.link_intersystems.ioc.declaration.location.BeanConfigBeanDeclarationLocation;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;

public class BeanFactoryMethodBeanDeclaration extends DefaultBeanDeclaration {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        BeanConfigBeanDeclarationLocation location = getLocation();
        sb.append(getBeanType().getSimpleName());
        sb.append(" ");
        sb.append(location.getBeanDeclaration().getBeanType().getName());
        sb.append(".");
        sb.append(getBeanName());

        sb.append("(");

        Iterator<Class<?>> parameterTypeIterator = Arrays.asList(beanFactoryMethod.getParameterTypes()).iterator();
        while (parameterTypeIterator.hasNext()) {
            Class<?> parameterType = parameterTypeIterator.next();
            sb.append(parameterType.getSimpleName());
            if (parameterTypeIterator.hasNext()) {
                sb.append(", ");
            }
        }

        sb.append(")");

        return sb.toString();
    }
}
