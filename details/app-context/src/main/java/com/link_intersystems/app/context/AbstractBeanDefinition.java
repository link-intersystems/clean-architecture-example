package com.link_intersystems.app.context;

import java.lang.reflect.Executable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Objects;
import java.util.function.Supplier;

abstract class AbstractBeanDefinition implements BeanDefinition {
    private URL resource;
    private BeanRef beanRef;

    public AbstractBeanDefinition(URL resource, BeanRef beanRef) {
        this.resource = resource;
        this.beanRef = Objects.requireNonNull(beanRef);
    }

    @Override
    public URL getResource() {
        return resource;
    }

    @Override
    public BeanRef getBeanRef() {
        return beanRef;
    }

    @Override
    public <T> T createBean(BeanFactory beanFactory) {
        BeanConstructor constructor = getBeanConstructor();

        if (constructor != null) {
            try {
                return constructor.createBean(beanFactory);
            } catch (Exception e) {
                throw new RuntimeException(constructor.toString(), e);
            }
        }

        throw new RuntimeException("No constructor found for bean " + getBeanRef().getName());
    }

    protected abstract BeanConstructor getBeanConstructor();

    protected Object[] resolveArgs(BeanFactory beanFactory, Executable executable, Class<?>[] parameterTypes) {
        Object[] args = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];

            if (Supplier.class.isAssignableFrom(parameterType)) {
                Type[] genericParameterTypes = executable.getGenericParameterTypes();
                ParameterizedType parameterizedType = (ParameterizedType) genericParameterTypes[i];
                Class<?> actualTypeArgument = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                args[i] = (Supplier) () -> beanFactory.getBean(actualTypeArgument);
            } else if (LazyBeanSetter.class.isAssignableFrom(parameterType)) {
                Type[] genericParameterTypes = executable.getGenericParameterTypes();
                ParameterizedType parameterizedType = (ParameterizedType) genericParameterTypes[i];
                Class<?> actualTypeArgument = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                LazyBeanSetter<Object> lazyBeanSetter = (LazyBeanSetter<Object>) beanFactory.getLazyBeanSetter(actualTypeArgument);
                args[i] = lazyBeanSetter;
            } else {
                Object bean = beanFactory.getBean(parameterType);
                args[i] = bean;
            }
        }

        return args;
    }

    @Override
    public String toString() {
        return getBeanConstructor().toString() + " '" + resource + "'";
    }
}
