package com.link_intersystems.ioc.definition;

import com.link_intersystems.ioc.api.LazyBeanSetter;
import com.link_intersystems.ioc.context.BeanFactory;

public class LazySetterParameterResolver extends GenericParameterResolver {
    public LazySetterParameterResolver(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    protected boolean isApplicable(Class<?> parameterType) {
        return LazyBeanSetter.class.isAssignableFrom(parameterType);
    }

    @Override
    protected Object getBean(BeanFactory beanFactory, Class<?> actualTypeArgument) {
        return (LazyBeanSetter<Object>) beanFactory.getLazyBeanSetter(actualTypeArgument);
    }
}
