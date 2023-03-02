package com.link_intersystems.app.context;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class BeanConfig {
    private URL resource;
    private final Class<?> type;
    private Class<?> beanConfigBeanType;

    public BeanConfig(URL resource, Class<?> type, Class<?> beanConfigBeanType) {
        this.resource = resource;
        this.type = type;
        this.beanConfigBeanType = beanConfigBeanType;
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

            String beanName = method.getName();
            BeanDeclaration beanDeclaration = new BeanDeclaration(returnType, beanName);
            BeanConfigBeanDefinition beanConfigBeanDefinition = new BeanConfigBeanDefinition(resource, beanDeclaration, beanConfigBeanType, method);
            beanDefinitions.add(beanConfigBeanDefinition);
        }

        return beanDefinitions;
    }
}
