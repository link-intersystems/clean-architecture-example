package com.link_intersystems.ioc;

import java.util.List;

public class BeansParameterResolver extends GenericParameterResolver {

    public BeansParameterResolver(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    protected boolean isApplicable(Class<?> parameterType) {
        return List.class.isAssignableFrom(parameterType);
    }

    @Override
    protected Object getBean(BeanFactory beanFactory, Class<?> actualTypeArgument) {
        return beanFactory.getBeans(actualTypeArgument);
    }
}
