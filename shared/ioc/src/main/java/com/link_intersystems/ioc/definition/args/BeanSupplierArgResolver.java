package com.link_intersystems.ioc.definition.args;

import com.link_intersystems.ioc.context.BeanFactory;

import java.util.function.Supplier;

public class BeanSupplierArgResolver extends GenericArgResolver {

    @Override
    protected boolean isApplicable(Class<?> parameterType) {
        return Supplier.class.isAssignableFrom(parameterType);
    }

    @Override
    protected Object getBean(BeanFactory beanFactory, Class<?> actualTypeArgument) {
        return (Supplier) () -> beanFactory.getBean(actualTypeArgument);
    }
}
