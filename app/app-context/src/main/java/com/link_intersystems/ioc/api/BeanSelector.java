package com.link_intersystems.ioc.api;

public interface BeanSelector<T> {

    public T select(String beanName);
}
