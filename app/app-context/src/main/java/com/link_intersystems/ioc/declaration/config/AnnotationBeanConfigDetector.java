package com.link_intersystems.ioc.declaration.config;

import com.link_intersystems.ioc.api.BeanConfig;
import com.link_intersystems.ioc.declaration.BeanConfigDetector;
import com.link_intersystems.ioc.declaration.BeanDeclaration;

public class AnnotationBeanConfigDetector implements BeanConfigDetector {
    @Override
    public boolean isBeanConfig(BeanDeclaration beanDeclaration) {
        Class<?> beanType = beanDeclaration.getBeanType();
        return beanType.isAnnotationPresent(BeanConfig.class);
    }
}
