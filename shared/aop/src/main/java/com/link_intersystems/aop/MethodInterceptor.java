package com.link_intersystems.aop;

public interface MethodInterceptor {
    public Object invoke(MethodInvocation methodInvocation) throws Throwable;
}
