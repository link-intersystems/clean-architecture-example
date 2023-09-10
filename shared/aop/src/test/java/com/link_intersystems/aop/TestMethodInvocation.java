package com.link_intersystems.aop;

import java.lang.reflect.Method;
import java.util.function.Supplier;

import static java.util.Objects.*;

class TestMethodInvocation implements MethodInvocation {

    private int invocationOrder = -1;
    private Supplier<Integer> invocationOrderSupplier;
    private Object result;

    public TestMethodInvocation(Supplier<Integer> invocationOrderSupplier) {
        this.invocationOrderSupplier = requireNonNull(invocationOrderSupplier);
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public Method getMethod() {
        return null;
    }

    @Override
    public Object[] getArgs() {
        return new Object[0];
    }

    @Override
    public Object proceed() throws Exception {
        invocationOrder = invocationOrderSupplier.get();
        return result;
    }

    public int getInvocationOrder() {
        return invocationOrder;
    }
}