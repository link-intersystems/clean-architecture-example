package com.link_intersystems.app.context;

interface BeanConstructor {

    public <T> T createBean() throws Exception;
}
