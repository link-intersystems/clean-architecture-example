package com.link_intersystems.jdbc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class AbstractConnectionDelegateTest implements InvocationHandler {

    private AbstractConnectionDelegate delegate;
    private Method invokedMethod;
    private Object[] invocationArgs;

    @BeforeEach
    void setUp() {
        Connection connectionProxy = (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(), new Class[]{Connection.class}, this);
        delegate = new AbstractConnectionDelegate(connectionProxy) {
        };
    }

    private static List<Method> delegationMethods() {
        List<Method> methods = new ArrayList<>();
        methods.addAll(Arrays.asList(Connection.class.getDeclaredMethods()));
        return methods;
    }

    @ParameterizedTest
    @MethodSource("delegationMethods")
    void methodDelegation(Method method) throws InvocationTargetException, IllegalAccessException {
        Object[] args = getTestArgs(method);
        method.invoke(delegate, args);

        Assertions.assertEquals(this.invokedMethod, method);
    }

    private Object[] getTestArgs(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] args = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            args[i] = testObject(parameterTypes[i]);
        }

        return args;
    }

    private Object testObject(Class<?> type) {
        if (boolean.class.equals(type)) {
            return true;
        } else if (int.class.equals(type)) {
            return 1;
        }
        return null;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        this.invokedMethod = method;
        this.invocationArgs = args;
        Class<?> returnType = method.getReturnType();
        return testObject(returnType);
    }
}