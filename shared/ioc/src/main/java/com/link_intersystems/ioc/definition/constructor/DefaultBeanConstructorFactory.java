package com.link_intersystems.ioc.definition.constructor;

import com.link_intersystems.ioc.context.BeanConstructor;
import com.link_intersystems.ioc.context.BeanConstructorFactory;
import com.link_intersystems.ioc.context.ConstructorArgsResolver;
import com.link_intersystems.ioc.declaration.BeanDeclaration;
import com.link_intersystems.ioc.declaration.BeanFactoryMethodBeanDeclaration;

public class DefaultBeanConstructorFactory implements BeanConstructorFactory {

    @Override
    public BeanConstructor createBeanConstructor(BeanDeclaration beanDeclaration, ConstructorArgsResolver constructorArgsResolver) {
        if (beanDeclaration instanceof BeanFactoryMethodBeanDeclaration) {
            BeanFactoryMethodBeanDeclaration beanFactoryMethodBeanDeclaration = (BeanFactoryMethodBeanDeclaration) beanDeclaration;
            return new FactoryMethodBeanConstructor(beanFactoryMethodBeanDeclaration, constructorArgsResolver);
        }

        return new DefaultBeanConstructor(beanDeclaration, constructorArgsResolver);
    }
}
