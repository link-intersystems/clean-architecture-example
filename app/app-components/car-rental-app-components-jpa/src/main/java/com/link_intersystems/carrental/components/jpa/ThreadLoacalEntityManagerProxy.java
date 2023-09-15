package com.link_intersystems.carrental.components.jpa;

import jakarta.persistence.EntityManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ThreadLoacalEntityManagerProxy implements InvocationHandler {

    private static final ThreadLocal<Map<String, EntityManager>> ENTITY_MANAGER_THREAD_LOCAL = new ThreadLocal<>() {
        @Override
        protected Map<String, EntityManager> initialValue() {
            return new HashMap<>();
        }
    };
    private String puName;

    public ThreadLoacalEntityManagerProxy(String puName) {
        this.puName = puName;
    }

    public static EntityManager createProxy(String puName) {
        return (EntityManager) Proxy.newProxyInstance(ThreadLoacalEntityManagerProxy.class.getClassLoader(), new Class[]{EntityManager.class}, new ThreadLoacalEntityManagerProxy(puName));
    }

    public static void setEntityManager(String puName, EntityManager entityManager) {
        Map<String, EntityManager> emByPu = ENTITY_MANAGER_THREAD_LOCAL.get();
        emByPu.put(puName, entityManager);
    }

    public static void removeEntityManager(String puName) {
        Map<String, EntityManager> emByPu = ENTITY_MANAGER_THREAD_LOCAL.get();
        emByPu.remove(puName);

        if (emByPu.isEmpty()) {
            ENTITY_MANAGER_THREAD_LOCAL.remove();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        EntityManager entityManager = getEntityManager();

        return method.invoke(entityManager, args);
    }

    private EntityManager getEntityManager() {
        Map<String, EntityManager> emByPu = ENTITY_MANAGER_THREAD_LOCAL.get();
        EntityManager entityManager = emByPu.get(puName);
        if (entityManager == null) {
            throw new IllegalStateException("No EntityManager available with persistence unit name " + puName);
        }
        return entityManager;
    }
}
