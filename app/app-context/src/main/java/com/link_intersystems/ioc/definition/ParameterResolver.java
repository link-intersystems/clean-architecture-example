package com.link_intersystems.ioc.definition;

import java.lang.reflect.Executable;

public interface ParameterResolver {

    public boolean canResolve(Executable executable, int index);

    public Object resolveParameter(Executable executable, int index);
}
