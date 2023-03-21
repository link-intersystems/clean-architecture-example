package com.link_intersystems.ioc.definition.args;

import com.link_intersystems.ioc.api.BeanSelector;
import com.link_intersystems.ioc.context.BeanFactory;

public class BeanSelectorArgResolver extends GenericArgResolver {

    @Override
    protected boolean isApplicable(Class<?> parameterType) {
        return BeanSelector.class.isAssignableFrom(parameterType);
    }

    @Override
    protected Object getBean(BeanFactory beanFactory, Class<?> actualTypeArgument) {
        return (BeanSelector<Object>) beanFactory.getBeanSelector(actualTypeArgument);
    }
}
