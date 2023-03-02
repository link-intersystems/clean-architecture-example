package com.link_intersystems.app.context;

interface BeanConstructor {

    public <T> T createBean(BeanFactory beanFactory) throws Exception;
}
