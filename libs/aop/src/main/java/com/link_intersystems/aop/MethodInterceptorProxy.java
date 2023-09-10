package com.link_intersystems.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import static java.util.Objects.*;

public class MethodInterceptorProxy implements InvocationHandler {

    public static final Object[] EMPTY_ARGS = new Object[0];

    public static class Builder {
        private MethodInterceptorChain methodInterceptorChain = new MethodInterceptorChain();

        public Builder withInterceptor(MethodInterceptor methodInterceptor) {
            methodInterceptorChain.addMethodInterceptor(methodInterceptor);
            return this;
        }

        public <T, I extends T> T build(Class<T> proxyType, I implementation) {
            MethodInterceptorProxy methodInterceptorProxy = new MethodInterceptorProxy(implementation, methodInterceptorChain);
            return (T) Proxy.newProxyInstance(proxyType.getClassLoader(), new Class[]{proxyType}, methodInterceptorProxy);
        }
    }

    private Object target;
    private final MethodInterceptor methodInterceptor;

    private MethodInterceptorProxy(Object target, MethodInterceptor methodInterceptor) {
        this.target = requireNonNull(target);
        this.methodInterceptor = requireNonNull(methodInterceptor);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        args = args == null ? EMPTY_ARGS : args;
        MethodInvocation methodInvocation = new DefaultMethodInvocation(target, method, args);
        return methodInterceptor.invoke(methodInvocation);
    }
}
