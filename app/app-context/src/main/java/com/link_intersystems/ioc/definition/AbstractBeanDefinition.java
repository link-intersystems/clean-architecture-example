package com.link_intersystems.ioc.definition;

import com.link_intersystems.ioc.context.BeanFactory;
import com.link_intersystems.ioc.declaration.BeanDeclaration;

import java.util.Objects;

abstract class AbstractBeanDefinition {
    private BeanDeclaration beanDeclaration;

    public AbstractBeanDefinition(BeanDeclaration beanDeclaration) {
        this.beanDeclaration = Objects.requireNonNull(beanDeclaration);
    }

    public BeanDeclaration getBeanDeclaration() {
        return beanDeclaration;
    }

    public <T> T createBean(BeanFactory beanFactory) {
        BeanConstructor constructor = getBeanConstructor();

        if (constructor != null) {
            try {
                return constructor.createBean(beanFactory);
            } catch (Exception e) {
                throw new RuntimeException(constructor.toString(), e);
            }
        }

        throw new RuntimeException("No constructor found for bean " + getBeanDeclaration().getBeanName());
    }

    protected abstract BeanConstructor getBeanConstructor();


    @Override
    public String toString() {
        return getBeanConstructor().toString();
    }
}
