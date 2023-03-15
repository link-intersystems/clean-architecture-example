package com.link_intersystems.ioc.definition.constructor;

import com.link_intersystems.ioc.context.BeanConstructor;
import com.link_intersystems.ioc.context.BeanFactory;
import com.link_intersystems.ioc.context.ConstructorArgsResolver;
import com.link_intersystems.ioc.declaration.location.BeanConfigBeanDeclarationLocation;
import com.link_intersystems.ioc.declaration.BeanDeclaration;
import com.link_intersystems.ioc.declaration.BeanFactoryMethodBeanDeclaration;

import java.lang.reflect.Method;

import static java.util.Objects.*;

class FactoryMethodBeanConstructor implements BeanConstructor {

    private BeanFactoryMethodBeanDeclaration beanDeclaration;
    private ConstructorArgsResolver constructorArgsResolver;

    public FactoryMethodBeanConstructor(BeanFactoryMethodBeanDeclaration beanDeclaration, ConstructorArgsResolver constructorArgsResolver) {
        this.beanDeclaration = requireNonNull(beanDeclaration);
        this.constructorArgsResolver = constructorArgsResolver;
    }

    @Override
    public <T> T createBean(BeanFactory beanFactory) throws Exception {
        BeanConfigBeanDeclarationLocation location = beanDeclaration.getLocation();
        BeanDeclaration beanConfigDeclaration = location.getBeanDeclaration();
        Class<?> beanConfigType = beanConfigDeclaration.getBeanType();
        String beanConfigName = beanConfigDeclaration.getBeanName();

        Object beanFactoryBean = beanFactory.getBean(beanConfigType, beanConfigName);

        Method beanFactoryMethod = beanDeclaration.getBeanFactoryMethod();
        Object[] args = constructorArgsResolver.resolveArgs(beanFactoryMethod);

        return (T) beanFactoryMethod.invoke(beanFactoryBean, args);
    }

}
