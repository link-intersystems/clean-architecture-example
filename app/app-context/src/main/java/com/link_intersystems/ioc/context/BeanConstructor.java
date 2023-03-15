package com.link_intersystems.ioc.context;

public interface BeanConstructor {

    public <T> T createBean(BeanFactory beanFactory) throws Exception;
}
