package com.link_intersystems.ioc;

interface BeanConstructor {

    public <T> T createBean(BeanFactory beanFactory) throws Exception;
}
