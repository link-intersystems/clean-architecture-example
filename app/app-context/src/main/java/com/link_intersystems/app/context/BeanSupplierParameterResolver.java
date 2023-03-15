package com.link_intersystems.app.context;

import java.util.function.Supplier;

public class BeanSupplierParameterResolver extends GenericParameterResolver {
    public BeanSupplierParameterResolver(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    protected boolean isApplicable(Class<?> parameterType) {
        return Supplier.class.isAssignableFrom(parameterType);
    }

    @Override
    protected Object getBean(BeanFactory beanFactory, Class<?> actualTypeArgument) {
        return (Supplier) () -> beanFactory.getBean(actualTypeArgument);
    }
}
