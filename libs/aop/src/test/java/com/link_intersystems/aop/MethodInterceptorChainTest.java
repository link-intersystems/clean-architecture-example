package com.link_intersystems.aop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class MethodInterceptorChainTest {

    private MethodInterceptorChain chain;

    @BeforeEach
    void setUp() {
        chain = new MethodInterceptorChain();
    }

    @Test
    void resultValue() throws Throwable {
        TestMethodInvocation methodInvocation = new TestMethodInvocation(() -> 0);
        methodInvocation.setResult("Test");
        assertEquals("Test", chain.invoke(methodInvocation));
    }

    @Test
    void emptyChain() throws Throwable {
        AtomicInteger atomicInteger = new AtomicInteger();

        TestMethodInvocation methodInvocation = new TestMethodInvocation(atomicInteger::getAndIncrement);
        chain.invoke(methodInvocation);

        assertEquals(0, methodInvocation.getInvocationOrder());
    }

    @Test
    void invoceChain() throws Throwable {
        AtomicInteger atomicInteger = new AtomicInteger();

        TestInterceptor firstInterceptor = new TestInterceptor(atomicInteger::getAndIncrement);
        chain.addMethodInterceptor(firstInterceptor);

        TestInterceptor secondInterceptor = new TestInterceptor(atomicInteger::getAndIncrement);
        chain.addMethodInterceptor(secondInterceptor);

        TestMethodInvocation methodInvocation = new TestMethodInvocation(atomicInteger::getAndIncrement);
        chain.invoke(methodInvocation);

        assertEquals(0, firstInterceptor.getInvocationOrder());
        assertEquals(1, secondInterceptor.getInvocationOrder());
        assertEquals(2, methodInvocation.getInvocationOrder());
    }
}