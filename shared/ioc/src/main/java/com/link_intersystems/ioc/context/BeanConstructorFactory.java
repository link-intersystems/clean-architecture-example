package com.link_intersystems.ioc.context;

import com.link_intersystems.ioc.declaration.BeanDeclaration;

public interface BeanConstructorFactory {
    BeanConstructor createBeanConstructor(BeanDeclaration beanDeclaration, ConstructorArgsResolver constructorArgsResolver);
}
