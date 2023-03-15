package com.link_intersystems.ioc.definition;

import com.link_intersystems.ioc.context.BeanFactory;

import java.lang.reflect.Executable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class GenericParameterResolver implements ParameterResolver {
    protected BeanFactory beanFactory;

    public GenericParameterResolver(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object resolveParameter(Executable executable, int index) {
        Type[] genericParameterTypes = executable.getGenericParameterTypes();
        ParameterizedType parameterizedType = (ParameterizedType) genericParameterTypes[index];
        Class<?> actualTypeArgument = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        return getBean(beanFactory, actualTypeArgument);
    }

    @Override
    public boolean canResolve(Executable executable, int index) {
        Class<?>[] parameterTypes = executable.getParameterTypes();
        return isApplicable(parameterTypes[index]);
    }

    protected abstract boolean isApplicable(Class<?> parameterType);

    protected abstract Object getBean(BeanFactory beanFactory, Class<?> actualTypeArgument);
}
