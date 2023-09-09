package com.link_intersystems.aop;

import java.lang.reflect.Method;

public interface MethodInvocation {

    public Method getMethod();

    public Object[] getArgs();

    Object proceed() throws Throwable;
}
