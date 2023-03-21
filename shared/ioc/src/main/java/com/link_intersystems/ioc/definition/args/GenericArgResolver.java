package com.link_intersystems.ioc.definition.args;

import com.link_intersystems.ioc.context.BeanFactory;

import java.lang.reflect.Executable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class GenericArgResolver implements ArgResolver {

    @Override
    public Object resolveParameter(BeanFactory beanFactory, Executable executable, int index) {
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
