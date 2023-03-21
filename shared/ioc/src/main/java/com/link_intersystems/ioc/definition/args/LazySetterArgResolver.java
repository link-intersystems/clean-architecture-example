package com.link_intersystems.ioc.definition.args;

import com.link_intersystems.ioc.api.LazyBeanSetter;
import com.link_intersystems.ioc.context.BeanFactory;

public class LazySetterArgResolver extends GenericArgResolver {

    @Override
    protected boolean isApplicable(Class<?> parameterType) {
        return LazyBeanSetter.class.isAssignableFrom(parameterType);
    }

    @Override
    protected Object getBean(BeanFactory beanFactory, Class<?> actualTypeArgument) {
        return beanFactory.getLazyBeanSetter(actualTypeArgument);
    }
}
