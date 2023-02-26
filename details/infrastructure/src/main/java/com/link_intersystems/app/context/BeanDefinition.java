package com.link_intersystems.app.context;

interface BeanDefinition {
    boolean isInstance(Class<?> type);

    boolean isNamed(String name);

    <T> T getBean();
}
