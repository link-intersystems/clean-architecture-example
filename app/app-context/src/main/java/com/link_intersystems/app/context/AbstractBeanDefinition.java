package com.link_intersystems.app.context;

import java.lang.reflect.Executable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

abstract class AbstractBeanDefinition implements BeanDefinition {
    private BeanDeclaration beanDeclaration;

    public AbstractBeanDefinition(BeanDeclaration beanDeclaration) {
        this.beanDeclaration = Objects.requireNonNull(beanDeclaration);
    }

    @Override
    public BeanDeclaration getBeanDeclaration() {
        return beanDeclaration;
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

        throw new RuntimeException("No constructor found for bean " + getBeanDeclaration().getId().getName());
    }

    protected abstract BeanConstructor getBeanConstructor();



    @Override
    public String toString() {
        return getBeanConstructor().toString();
    }
}
