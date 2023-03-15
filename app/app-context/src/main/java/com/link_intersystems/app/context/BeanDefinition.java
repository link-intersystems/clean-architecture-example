package com.link_intersystems.app.context;

public interface BeanDefinition {
    BeanDeclaration getBeanDeclaration();

    <T> T createBean(BeanFactory beanFactory);
}
