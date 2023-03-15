package com.link_intersystems.ioc;

import java.util.List;

public interface BeanFactory {
    default <T> T getBean(Class<T> type) {
        return getBean(type, null);
    }

    <T> T getBean(Class<T> type, String name);

    <T> LazyBeanSetter<T> getLazyBeanSetter(Class<T> type);

    <T> BeanSelector<T> getBeanSelector(Class<T> type);

    <T> List<T> getBeans(Class<T> type);
}
