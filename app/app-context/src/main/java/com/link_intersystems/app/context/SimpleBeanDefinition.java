package com.link_intersystems.app.context;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

class SimpleBeanDefinition extends AbstractBeanDefinition {

    public SimpleBeanDefinition(URL resource, BeanDeclaration beanDeclaration) {
        super(resource, beanDeclaration);
    }

    @Override
    protected BeanConstructor getBeanConstructor() {
        BeanDeclaration beanDeclaration = getBeanRef();
        Class<?> beanType = beanDeclaration.getType();
        Constructor[] constructors = beanType.getDeclaredConstructors();

        if (constructors.length > 0) {
            Constructor constructor = constructors[0];

            return new BeanConstructor() {

                Class<?>[] parameterTypes = constructor.getParameterTypes();

                @Override
                public <T> T createBean(BeanFactory beanFactory) throws Exception {
                    Object[] constructorArgs = resolveArgs(beanFactory, constructor, parameterTypes);
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
