package com.link_intersystems.jdbc.tx;

import java.util.ArrayList;
import java.util.List;

class ClassUtils {

    public static <T> List<Class<?>> getInterfaces(T object) {
        Class<?> aClass = object.getClass();
        return ClassUtils.getInterfaces(aClass);
    }

    public static <T> List<Class<?>> getInterfaces(Class<?> aClass) {
        List<Class<?>> interfaces = new ArrayList<>();

        if (aClass == null) {
            return interfaces;
        }

        if (aClass.isInterface()) {
            interfaces.add(aClass);
        }

        Class<?>[] superInterfaces = aClass.getInterfaces();

        for (Class<?> superInterface : superInterfaces) {
            interfaces.addAll(ClassUtils.getInterfaces(superInterface));
        }

        Class<?> superClass = aClass.getSuperclass();
        List<Class<?>> superClassInterfaces = ClassUtils.getInterfaces(superClass);
        interfaces.addAll(superClassInterfaces);

        return interfaces;
    }
}
