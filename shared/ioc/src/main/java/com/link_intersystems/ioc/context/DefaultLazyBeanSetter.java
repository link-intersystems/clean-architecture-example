package com.link_intersystems.ioc.context;

import com.link_intersystems.ioc.api.LazyBeanSetter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class DefaultLazyBeanSetter<T> implements LazyBeanSetter<T> {

    private List<Consumer<T>> beanSetters = new ArrayList<>();
    private T bean;

    void setBean(T bean) {
        this.bean = bean;
        if (!beanSetters.isEmpty()) {
            beanSetters.forEach(bs -> bs.accept(bean));
        }
    }

    @Override
    public void setBean(Consumer<T> beanSetter) {
        this.beanSetters.add(beanSetter);
        if (bean != null) {
            beanSetter.accept(bean);
        }
    }

}
