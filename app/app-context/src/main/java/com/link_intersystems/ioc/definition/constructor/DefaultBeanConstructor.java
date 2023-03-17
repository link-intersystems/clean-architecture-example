package com.link_intersystems.ioc.definition.constructor;

import com.link_intersystems.ioc.context.BeanConstructor;
import com.link_intersystems.ioc.context.BeanFactory;
import com.link_intersystems.ioc.context.ConstructorArgsResolver;
import com.link_intersystems.ioc.declaration.BeanDeclaration;

import java.lang.reflect.Constructor;

import static java.util.Objects.*;

class DefaultBeanConstructor implements BeanConstructor {


    private BeanDeclaration beanDeclaration;
    private ConstructorArgsResolver constructorArgsResolver;

    public DefaultBeanConstructor(BeanDeclaration beanDeclaration, ConstructorArgsResolver constructorArgsResolver) {
        this.beanDeclaration = requireNonNull(beanDeclaration);
        this.constructorArgsResolver = constructorArgsResolver;
    }

    private Constructor<?> getConstructor(BeanDeclaration beanDeclaration) {
        Class<?> beanType = beanDeclaration.getBeanType();
        Constructor[] constructors = beanType.getDeclaredConstructors();

        if (constructors.length > 0) {
            return constructors[0];
        }

        return null;
    }

    @Override
    public <T> T createBean(BeanFactory beanFactory) throws Exception {
        Constructor<?> constructor = getConstructor(beanDeclaration);
        if (constructor == null) {
            throw new IllegalStateException("Unable to find a constructor for " + beanDeclaration);
        }
        Object[] args = constructorArgsResolver.resolveArgs(constructor);
        if (!constructor.canAccess(null)) {
            constructor.setAccessible(true);
        }
        return (T) constructor.newInstance(args);
    }
}
