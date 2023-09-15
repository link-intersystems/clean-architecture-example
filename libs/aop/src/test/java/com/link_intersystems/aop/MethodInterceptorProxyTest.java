package com.link_intersystems.aop;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class MethodInterceptorProxyTest {

    @Test
    void proxy() {
        AtomicInteger atomicInteger = new AtomicInteger();
        TestInterceptor interceptor = new TestInterceptor(atomicInteger::getAndIncrement);

        class DefaultGreetingService implements GreetingService {

            @Override
            public String sayHello(String who) {
                return "Hello " + who;
            }
        }


        GreetingService greetingService = new MethodInterceptorProxy.Builder()
                .withInterceptor(interceptor)
                .build(GreetingService.class, new DefaultGreetingService());

        String greeting = greetingService.sayHello("René");
        assertEquals("Hello René", greeting);

        assertEquals(0, interceptor.getInvocationOrder());
    }


    static interface GreetingService {

        public String sayHello(String who);
    }

}