package com.link_intersystems.ioc.context;

import com.link_intersystems.ioc.definition.args.ArgResolver;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

class DefaultConstructorArgsResolver implements ConstructorArgsResolver {

    private BeanFactory beanFactory;

    public DefaultConstructorArgsResolver(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object[] resolveArgs(Executable executable) {
        Class<?>[] parameterTypes = executable.getParameterTypes();
        Object[] args = new Object[parameterTypes.length];

        List<ArgResolver> argResolvers = getParameterResolvers();

        nextParameter:
        for (int parameterIndex = 0; parameterIndex < parameterTypes.length; parameterIndex++) {
            for (ArgResolver argResolver : argResolvers) {
                if (argResolver.canResolve(executable, parameterIndex)) {
                    args[parameterIndex] = argResolver.resolveParameter(beanFactory, executable, parameterIndex);
                    continue nextParameter;
                }
            }
        }

        return args;
    }

    private List<ArgResolver> getParameterResolvers() {
        List<ArgResolver> argResolvers = new ArrayList<>();

        for (ArgResolver argResolver : ServiceLoader.load(ArgResolver.class)) {
            argResolvers.add(argResolver);
        }

        argResolvers.add(new BeanFactoryArgResolver());

        return argResolvers;
    }
}
