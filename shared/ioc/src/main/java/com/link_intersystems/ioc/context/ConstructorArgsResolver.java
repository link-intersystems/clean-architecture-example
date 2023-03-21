package com.link_intersystems.ioc.context;

import java.lang.reflect.Executable;

public interface ConstructorArgsResolver {
    Object[] resolveArgs(Executable executable);
}
