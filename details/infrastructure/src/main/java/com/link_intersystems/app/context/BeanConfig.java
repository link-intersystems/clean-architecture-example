package com.link_intersystems.app.context;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class BeanConfig {
    private URL resource;
    private final Class<?> type;
    private Class<?> beanConfigBeanType;
    private final BeanFactory beanFactory;

    public BeanConfig(URL resource, Class<?> type, Class<?> beanConfigBeanType, BeanFactory beanFactory) {
        this.resource = resource;
        this.type = type;
        this.beanConfigBeanType = beanConfigBeanType;
        this.beanFactory = beanFactory;
    }

    public List<BeanDefinition> getBeanDefinitions() {
        List<BeanDefinition> beanDefinitions = new ArrayList<>();

        for (Method method : type.getMethods()) {
            if (method.getDeclaringClass().equals(Object.class)) {
                continue;
            }

            Class<?> returnType = method.getReturnType();

            if (Void.TYPE.equals(returnType)) {
                continue;
            }

            BeanConfigBeanDefinition beanConfigBeanDefinition = new BeanConfigBeanDefinition(resource, returnType, beanConfigBeanType, beanFactory, method);
            beanDefinitions.add(beanConfigBeanDefinition);
        }

        return beanDefinitions;
    }
}
