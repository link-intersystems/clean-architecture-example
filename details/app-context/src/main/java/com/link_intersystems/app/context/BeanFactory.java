package com.link_intersystems.app.context;

public interface BeanFactory {
    default <T> T getBean(Class<T> type) {
        return getBean(type, null);
    }

    <T> T getBean(Class<T> type, String name);
}
