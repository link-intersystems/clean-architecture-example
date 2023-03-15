package com.link_intersystems.ioc;

import java.util.function.Consumer;

public interface LazyBeanSetter<T> {

    /**
     * Register a {@link Consumer} that will be invoked as soon as the bean of type T is available.
     *
     * @param beanSetter
     */
    void setBean(Consumer<T> beanSetter);
}
