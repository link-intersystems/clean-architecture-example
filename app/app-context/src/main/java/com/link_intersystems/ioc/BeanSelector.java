package com.link_intersystems.ioc;

public interface BeanSelector<T> {

    public T select(String beanName);
}
