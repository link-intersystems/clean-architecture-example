package com.link_intersystems.ioc;

public class BeanSelectorParameterResolver extends GenericParameterResolver {

    public BeanSelectorParameterResolver(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    protected boolean isApplicable(Class<?> parameterType) {
        return BeanSelector.class.isAssignableFrom(parameterType);
    }

    @Override
    protected Object getBean(BeanFactory beanFactory, Class<?> actualTypeArgument) {
        return (BeanSelector<Object>) beanFactory.getBeanSelector(actualTypeArgument);
    }
}
