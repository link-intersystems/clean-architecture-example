package com.link_intersystems.aop;

import java.util.function.Supplier;

import static java.util.Objects.*;

class TestInterceptor implements MethodInterceptor {

    private int invocationOrder = -1;
    private Supplier<Integer> invocationOrderSupplier;

    public TestInterceptor(Supplier<Integer> invocationOrderSupplier) {
        this.invocationOrderSupplier = requireNonNull(invocationOrderSupplier);
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Exception {
        invocationOrder = invocationOrderSupplier.get();
        return methodInvocation.proceed();
    }

    public int getInvocationOrder() {
        return invocationOrder;
    }
}