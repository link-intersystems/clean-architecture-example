package com.link_intersystems.aop;

import java.lang.reflect.Method;

import static java.util.Objects.*;

public class DefaultMethodInvocation implements MethodInvocation {

    private Object target;
    private Method method;
    private Object[] args;

    public DefaultMethodInvocation(Object target, Method method, Object... args) {
        this.target = requireNonNull(target);
        this.method = requireNonNull(method);
        this.args = requireNonNull(args);
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArgs() {
        return args;
    }

    @Override
    public Object proceed() throws Exception {
        return method.invoke(target, args);
    }

}
