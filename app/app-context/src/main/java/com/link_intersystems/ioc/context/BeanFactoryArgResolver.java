package com.link_intersystems.ioc.context;

import com.link_intersystems.ioc.definition.args.ArgResolver;

import java.lang.reflect.Executable;

class BeanFactoryArgResolver implements ArgResolver {

    @Override
    public boolean canResolve(Executable executable, int index) {
        return true;
    }

    @Override
    public Object resolveParameter(BeanFactory beanFactory, Executable executable, int index) {
        Class<?> parameterType = executable.getParameterTypes()[index];
        return beanFactory.getBean(parameterType);
    }
}
