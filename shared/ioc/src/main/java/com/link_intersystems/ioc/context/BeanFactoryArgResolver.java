package com.link_intersystems.ioc.context;

import com.link_intersystems.ioc.definition.args.ArgResolver;

import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;

class BeanFactoryArgResolver implements ArgResolver {

    @Override
    public boolean canResolve(Executable executable, int index) {
        return true;
    }

    @Override
    public Object resolveParameter(BeanFactory beanFactory, Executable executable, int index) {
        Class<?> parameterType = executable.getParameterTypes()[index];
        String beanName = getBeanName(executable, index);
        return beanFactory.getBean(parameterType, beanName);
    }

    private String getBeanName(Executable executable, int parameterIndex) {
        Parameter[] parameters = executable.getParameters();
        Parameter parameter = parameters[parameterIndex];
        String name = parameter.getName();

        if (isSynthesizedName(name)) {
            return null;
        }

        return name;
    }

    private static boolean isSynthesizedName(String name) {
        return name.startsWith("arg");
    }
}
