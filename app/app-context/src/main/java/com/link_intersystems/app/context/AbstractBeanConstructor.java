package com.link_intersystems.app.context;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractBeanConstructor implements BeanConstructor {

    @Override
    public <T> T createBean(BeanFactory beanFactory) throws Exception {
        Object[] args = resolveArgs(beanFactory);
        try {
            return createBean(beanFactory, args);
        } catch (Exception e) {
            throw new Exception("Can not create bean " + this, e);
        }
    }

    protected abstract <T> T createBean(BeanFactory beanFactory, Object[] args) throws Exception;

    protected Object[] resolveArgs(BeanFactory beanFactory) {
        Executable executable = getExecutable();
        Class<?>[] parameterTypes = getParameterTypes();
        Object[] args = new Object[parameterTypes.length];

        List<ParameterResolver> parameterResolvers = getParameterResolvers(beanFactory);

        nextParameter:
        for (int parameterIndex = 0; parameterIndex < parameterTypes.length; parameterIndex++) {
            for (ParameterResolver parameterResolver : parameterResolvers) {
                if (parameterResolver.canResolve(executable, parameterIndex)) {
                    args[parameterIndex] = parameterResolver.resolveParameter(executable, parameterIndex);
                    continue nextParameter;
                }
            }
        }

        return args;
    }

    private List<ParameterResolver> getParameterResolvers(BeanFactory beanFactory) {
        List<ParameterResolver> parameterResolvers = new ArrayList<>();

        parameterResolvers.add(new BeansParameterResolver(beanFactory));
        parameterResolvers.add(new BeanSupplierParameterResolver(beanFactory));
        parameterResolvers.add(new LazySetterParameterResolver(beanFactory));
        parameterResolvers.add(new BeanSelectorParameterResolver(beanFactory));

        parameterResolvers.add(new DefaultParameterResolver(beanFactory));

        return parameterResolvers;
    }

    protected abstract Executable getExecutable();

    protected abstract Class<?>[] getParameterTypes();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getExecutable().getName());
        sb.append('(');
        sb.append(toString(getParameterTypes()));
        return sb.toString();
    }

    protected String toString(Class<?>[] parameterTypes) {
        StringBuilder sb = new StringBuilder();
        List<Class<?>> parameterTypeList = Arrays.asList(parameterTypes);
        Iterator<Class<?>> iterator = parameterTypeList.iterator();
        while (iterator.hasNext()) {
            Class<?> next = iterator.next();
            sb.append(next.getSimpleName());
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
