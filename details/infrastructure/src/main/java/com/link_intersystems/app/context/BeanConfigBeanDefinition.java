package com.link_intersystems.app.context;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

class BeanConfigBeanDefinition extends AbstractBeanDefinition {
    private Class<?> beanConfigBeanType;
    private Method beanFactoryMethod;

    public BeanConfigBeanDefinition(URL resource, Class<?> type, Class<?> beanConfigBeanType, BeanFactory beanFactory, Method beanFactoryMethod) {
        super(resource, type, beanFactory);
        this.beanConfigBeanType = beanConfigBeanType;
        this.beanFactoryMethod = beanFactoryMethod;
    }

    @Override
    protected BeanConstructor getBeanConstructor() {


        return new BeanConstructor() {

            Class<?>[] parameterTypes = beanFactoryMethod.getParameterTypes();

            @Override
            public <T> T createBean() throws Exception {
                try {
                    Object[] factoryArgs = resolveArgs(parameterTypes);
                    Object beanFactoryBean = getBeanFactory().getBean(beanConfigBeanType, beanConfigBeanType.getName());
                    return (T) beanFactoryMethod.invoke(beanFactoryBean, factoryArgs);
                } catch (Exception e) {
                    throw new Exception("Can not create bean " + getType().getName() + "(" + Arrays.toString(parameterTypes) + ")", e);
                }
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(beanConfigBeanType.getName());
                sb.append(".");
                sb.append(beanFactoryMethod.getName());
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
}

