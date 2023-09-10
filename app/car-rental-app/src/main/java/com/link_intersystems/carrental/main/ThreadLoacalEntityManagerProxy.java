package com.link_intersystems.carrental.main;

import jakarta.persistence.EntityManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ThreadLoacalEntityManagerProxy implements InvocationHandler {

    public static EntityManager createEntityManager() {
        return (EntityManager) Proxy.newProxyInstance(ThreadLoacalEntityManagerProxy.class.getClassLoader(), new Class[]{EntityManager.class}, new ThreadLoacalEntityManagerProxy());
    }

    private static final ThreadLocal<EntityManager> ENTITY_MANAGER_THREAD_LOCAL = new ThreadLocal<>();

    public static void setEntityManager(EntityManager entityManager) {
        if (entityManager == null) {
            ENTITY_MANAGER_THREAD_LOCAL.remove();
        } else {
            ENTITY_MANAGER_THREAD_LOCAL.set(entityManager);
        }
    }

    public static EntityManager getEntityManager() {
        return ENTITY_MANAGER_THREAD_LOCAL.get();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        EntityManager entityManager = ENTITY_MANAGER_THREAD_LOCAL.get();
        if (entityManager == null) {
            throw new IllegalStateException("No EntityManager set");
        }
        return method.invoke(entityManager, args);
    }
}
