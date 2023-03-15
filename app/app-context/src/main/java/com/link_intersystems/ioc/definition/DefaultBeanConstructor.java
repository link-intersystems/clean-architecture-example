package com.link_intersystems.ioc.definition;

import com.link_intersystems.ioc.context.BeanFactory;
import com.link_intersystems.ioc.declaration.BeanDeclaration;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;

public class DefaultBeanConstructor extends AbstractBeanDefinition implements BeanConstructor {


    public DefaultBeanConstructor(BeanDeclaration beanDeclaration) {
        super(beanDeclaration);
    }


    @Override
    protected BeanConstructor getBeanConstructor() {
        BeanDeclaration beanDeclaration = getBeanDeclaration();
        Class<?> beanType = beanDeclaration.getBeanType();
        Constructor[] constructors = beanType.getDeclaredConstructors();

        if (constructors.length > 0) {
            Constructor constructor = constructors[0];

            return new AbstractBeanConstructor() {

                @Override
                protected <T> T createBean(BeanFactory beanFactory, Object[] args) throws Exception {
                    return (T) constructor.newInstance(args);
                }

                @Override
                protected Executable getExecutable() {
                    return constructor;
                }

                @Override
                protected Class<?>[] getParameterTypes() {
                    return constructor.getParameterTypes();
                }


            };
        }

        return null;
    }


}
