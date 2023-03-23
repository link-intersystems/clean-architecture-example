package com.link_intersystems.jdbc.tx;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import static java.util.Objects.*;

public class TransactionProxy implements InvocationHandler {

    public static <T> T create(T object, TransactionManager tm) {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        List<Class<?>> interfaces = ClassUtils.getInterfaces(object);
        TransactionProxy transactionProxy = new TransactionProxy(tm, object);
        return (T) Proxy.newProxyInstance(contextClassLoader, interfaces.toArray(Class[]::new), transactionProxy);
    }

    private final TransactionTemplate transactionTemplate;
    private Object target;

    private TransactionProxy(TransactionManager transactionManager, Object target) {
        transactionTemplate = new TransactionTemplate(transactionManager);
        this.target = requireNonNull(target);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return transactionTemplate.doInTransaction(() -> method.invoke(target, args));
    }

}
