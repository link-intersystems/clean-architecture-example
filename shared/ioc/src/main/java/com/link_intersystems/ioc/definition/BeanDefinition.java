package com.link_intersystems.ioc.definition;

import com.link_intersystems.ioc.declaration.BeanDeclaration;

public class BeanDefinition<T> {

    private T bean;
    private BeanDeclaration beanDeclaration;

    public BeanDefinition(T bean, BeanDeclaration beanDeclaration) {
        this.bean = bean;
        this.beanDeclaration = beanDeclaration;
    }

    public T getBean() {
        return bean;
    }

    public BeanDeclaration getDeclaration() {
        return beanDeclaration;
    }

    @Override
    public String toString() {
        return beanDeclaration.getBeanType().getName() + " (" + beanDeclaration.getBeanName() + ")" ;
    }
}
