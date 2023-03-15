package com.link_intersystems.ioc;

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
            BeanId beanId = new BeanId(returnType, beanName);
            BeanDeclaration beanDeclaration = new BeanDeclaration(resource, beanId);
            FactoryMethodBeanDefinition factoryMethodBeanDefinition = new FactoryMethodBeanDefinition(beanDeclaration, beanConfigBeanType, method);
            beanDefinitions.add(factoryMethodBeanDefinition);
        }

        return beanDefinitions;
    }
}
