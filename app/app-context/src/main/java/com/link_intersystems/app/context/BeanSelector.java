package com.link_intersystems.app.context;

public interface BeanSelector<T> {

    public T select(String beanName);
}
