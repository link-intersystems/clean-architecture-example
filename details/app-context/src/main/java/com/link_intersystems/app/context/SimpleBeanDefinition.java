package com.link_intersystems.app.context;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

class SimpleBeanDefinition extends AbstractBeanDefinition {

    public SimpleBeanDefinition(URL resource, Class<?> type, BeanFactory beanFactory) {
        super(resource, type, beanFactory);
    }

    @Override
    protected BeanConstructor getBeanConstructor() {
        Constructor[] constructors = getType().getDeclaredConstructors();

        if (constructors.length > 0) {
            Constructor constructor = constructors[0];

            return new BeanConstructor() {

                Class<?>[] parameterTypes = constructor.getParameterTypes();

                @Override
                public <T> T createBean() throws Exception {
                    Object[] constructorArgs = resolveArgs(parameterTypes);
                    return (T) constructor.newInstance(constructorArgs);
                }

                @Override
                public String toString() {
                    StringBuilder sb = new StringBuilder();
                    sb.append(constructor.getDeclaringClass().getName());
                    sb.append("(");
                    List<Class<?>> parameterTypeList = Arrays.asList(parameterTypes);
                    Iterator<Class<?>> iterator = parameterTypeList.iterator();
                    while (iterator.hasNext()) {
                        Class<?> next = iterator.next();
                        sb.append(next.getSimpleName());
                        if (iterator.hasNext()) {
                            sb.append(", ");
                        }
                    }
                    sb.append(")");
                    return sb.toString();
                }
            };
        }

        return null;
    }

}
