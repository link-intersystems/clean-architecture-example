package com.link_intersystems.ioc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;

class ConstructorBeanDefinition extends AbstractBeanDefinition {


    public ConstructorBeanDefinition(BeanDeclaration beanDeclaration) {
        super(beanDeclaration);
    }


    @Override
    protected BeanConstructor getBeanConstructor() {
        BeanDeclaration beanDeclaration = getBeanDeclaration();
        Class<?> beanType = beanDeclaration.getId().getType();
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
