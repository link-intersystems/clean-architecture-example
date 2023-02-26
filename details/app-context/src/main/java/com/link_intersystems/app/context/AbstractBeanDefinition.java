package com.link_intersystems.app.context;

import java.lang.reflect.Executable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Objects;
import java.util.function.Supplier;

abstract class AbstractBeanDefinition implements BeanDefinition {
    private URL resource;
    private Class<?> type;
    private BeanFactory beanFactory;

    public AbstractBeanDefinition(URL resource, Class<?> type, BeanFactory beanFactory) {
        this.resource = resource;
        this.type = Objects.requireNonNull(type);
        this.beanFactory = Objects.requireNonNull(beanFactory);
    }

    public Class<?> getType() {
        return type;
    }

    protected BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public boolean isNamed(String name) {
        if (type.getName().equals(name)) {
            return true;
        }

        return type.getSimpleName().equals(name);
    }

    @Override
    public boolean isInstance(Class<?> type) {
        return type.isAssignableFrom(this.type);
    }

    @Override
    public <T> T getBean() {
        BeanConstructor constructor = getBeanConstructor();
        if (constructor != null) {
            try {
                return constructor.createBean();
            } catch (Exception e) {
                throw new RuntimeException(constructor.toString(), e);
            }
        }

        throw new RuntimeException("No constructor found for bean " + type.getName());
    }

    protected abstract BeanConstructor getBeanConstructor();

    protected Object[] resolveArgs(Executable executable, Class<?>[] parameterTypes) {
        Object[] args = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            if (Supplier.class.isAssignableFrom(parameterTypes[i])) {
                Type[] genericParameterTypes = executable.getGenericParameterTypes();
                ParameterizedType parameterizedType = (ParameterizedType) genericParameterTypes[i];
                Class<?> actualTypeArgument = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                args[i] = (Supplier) () -> beanFactory.getBean(actualTypeArgument);
            } else {
                Object bean = beanFactory.getBean(parameterTypes[i]);
                args[i] = bean;
            }
        }

        return args;
    }

    @Override
    public String toString() {
        return getBeanConstructor().toString() + " defined in " + resource;
    }
}
