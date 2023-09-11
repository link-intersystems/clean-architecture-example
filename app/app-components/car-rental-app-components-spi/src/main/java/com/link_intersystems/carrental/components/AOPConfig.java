package com.link_intersystems.carrental.components;

import com.link_intersystems.aop.MethodInterceptor;
import com.link_intersystems.aop.MethodInterceptorProxy;

import java.util.List;

public class AOPConfig {

    private final MethodInterceptorProxy.Builder proxyBuilder;

    public AOPConfig(List<MethodInterceptor> methodInterceptors) {
        proxyBuilder = new MethodInterceptorProxy.Builder();
        methodInterceptors.forEach(proxyBuilder::withInterceptor);
    }

    public <T, I extends T> T applyAOP(Class<T> type, I implementation) {
        return proxyBuilder.build(type, implementation);
    }
}
