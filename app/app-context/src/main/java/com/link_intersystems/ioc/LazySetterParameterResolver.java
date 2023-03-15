package com.link_intersystems.ioc;

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
