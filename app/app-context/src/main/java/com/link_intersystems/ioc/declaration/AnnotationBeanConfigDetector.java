package com.link_intersystems.ioc.declaration;

import com.link_intersystems.ioc.api.BeanConfig;

public class AnnotationBeanConfigDetector implements BeanConfigDetector {
    @Override
    public boolean isBeanConfig(BeanDeclaration beanDeclaration) {
        Class<?> beanType = beanDeclaration.getBeanType();
        return beanType.isAnnotationPresent(BeanConfig.class);
    }
}
