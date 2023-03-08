package com.link_intersystems.app.context;

import java.net.URL;

public interface BeanDefinition {

    URL getResource();

    BeanDeclaration getBeanDeclaration();

    <T> T createBean(BeanFactory beanFactory);
}
