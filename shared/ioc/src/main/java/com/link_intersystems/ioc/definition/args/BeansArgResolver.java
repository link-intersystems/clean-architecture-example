package com.link_intersystems.ioc.definition.args;

import com.link_intersystems.ioc.context.BeanFactory;

import java.util.List;

public class BeansArgResolver extends GenericArgResolver {

    @Override
    protected boolean isApplicable(Class<?> parameterType) {
        return List.class.isAssignableFrom(parameterType);
    }

    @Override
    protected Object getBean(BeanFactory beanFactory, Class<?> actualTypeArgument) {
        return beanFactory.getBeans(actualTypeArgument);
    }
}
