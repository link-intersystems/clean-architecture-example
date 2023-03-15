package com.link_intersystems.ioc.definition;

import com.link_intersystems.ioc.context.BeanFactory;

public interface BeanDefinition {
    BeanDeclaration getBeanDeclaration();

    <T> T createBean(BeanFactory beanFactory);
}
