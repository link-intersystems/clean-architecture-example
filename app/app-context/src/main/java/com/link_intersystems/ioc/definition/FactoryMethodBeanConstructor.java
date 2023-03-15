package com.link_intersystems.ioc.definition;

import com.link_intersystems.ioc.context.BeanFactory;
import com.link_intersystems.ioc.declaration.BeanConfigBeanDeclarationLocation;
import com.link_intersystems.ioc.declaration.BeanConfigDetector;
import com.link_intersystems.ioc.declaration.BeanDeclaration;
import com.link_intersystems.ioc.declaration.BeanFactoryMethodBeanDeclaration;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;

public class FactoryMethodBeanConstructor extends AbstractBeanDefinition implements BeanConstructor {
    private BeanFactoryMethodBeanDeclaration beanFactoryMethodBeanDeclaration;

    public FactoryMethodBeanConstructor(BeanFactoryMethodBeanDeclaration beanFactoryMethodBeanDeclaration) {
        super(beanFactoryMethodBeanDeclaration);
        this.beanFactoryMethodBeanDeclaration = beanFactoryMethodBeanDeclaration;
    }

    @Override
    protected BeanConstructor getBeanConstructor() {
        Method beanFactoryMethod = beanFactoryMethodBeanDeclaration.getBeanFactoryMethod();
        BeanConfigBeanDeclarationLocation location = beanFactoryMethodBeanDeclaration.getLocation();
        BeanDeclaration beanDeclaration = location.getBeanDeclaration();
        Class<?> beanConfigType = beanDeclaration.getBeanType();

        return new AbstractBeanConstructor() {

            Class<?>[] parameterTypes = beanFactoryMethod.getParameterTypes();

            @Override
            protected <T> T createBean(BeanFactory beanFactory, Object[] args) throws Exception {

                Object beanFactoryBean = beanFactory.getBean(beanConfigType, beanDeclaration.getBeanName());
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
                sb.append(beanConfigType.getName());
                sb.append(".");
                sb.append(beanFactoryMethod.getName());
                sb.append("(");

                sb.append(toString(parameterTypes));
                return sb.toString();
            }
        };
    }
}
