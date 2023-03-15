package com.link_intersystems.ioc.definition.args;

import com.link_intersystems.ioc.context.BeanFactory;

import java.lang.reflect.Executable;

public interface ArgResolver {

    public boolean canResolve(Executable executable, int index);

    public Object resolveParameter(BeanFactory beanFactory, Executable executable, int index);
}
