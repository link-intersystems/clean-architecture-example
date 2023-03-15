package com.link_intersystems.ioc;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;

class FactoryMethodBeanDefinition extends AbstractBeanDefinition {
    private Class<?> beanConfigBeanType;
    private Method beanFactoryMethod;

    public FactoryMethodBeanDefinition(BeanDeclaration beanDeclaration, Class<?> beanConfigBeanType, Method beanFactoryMethod) {
        super(beanDeclaration);
        this.beanConfigBeanType = beanConfigBeanType;
        this.beanFactoryMethod = beanFactoryMethod;
    }

    @Override
    protected BeanConstructor getBeanConstructor() {

        return new AbstractBeanConstructor() {

            Class<?>[] parameterTypes = beanFactoryMethod.getParameterTypes();

            @Override
            protected <T> T createBean(BeanFactory beanFactory, Object[] args) throws Exception {
                Object beanFactoryBean = beanFactory.getBean(beanConfigBeanType, beanConfigBeanType.getName());
                return (T) beanFactoryMethod.invoke(beanFactoryBean, args);
            }

            @Override
            protected Executable getExecutable() {
                return beanFactoryMethod;
            }

            @Override
            protected Class<?>[] getParameterTypes() {
                return beanFactoryMethod.getParameterTypes();
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(beanConfigBeanType.getName());
                sb.append(".");
                sb.append(beanFactoryMethod.getName());
                sb.append("(");

                sb.append(toString(parameterTypes));
                return sb.toString();
            }
        };
    }
}

