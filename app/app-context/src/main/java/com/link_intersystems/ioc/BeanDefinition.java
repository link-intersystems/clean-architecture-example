package com.link_intersystems.ioc;

public interface BeanDefinition {
    BeanDeclaration getBeanDeclaration();

    <T> T createBean(BeanFactory beanFactory);
}
