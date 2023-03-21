package com.link_intersystems.ioc.declaration;

import com.link_intersystems.ioc.declaration.location.BeanConfigBeanDeclarationLocation;

import java.lang.reflect.Method;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class BeanConfigBeanDeclaration extends AbstractList<BeanDeclaration> implements BeanDeclaration {

    private List<BeanDeclaration> beanDeclarations;
    private BeanDeclaration beanConfigBeanDeclaration;

    public BeanConfigBeanDeclaration(BeanDeclaration beanConfigBeanDeclaration) {
        this.beanConfigBeanDeclaration = beanConfigBeanDeclaration;
    }

    @Override
    public BeanDeclarationLocation getLocation() {
        return beanConfigBeanDeclaration.getLocation();
    }

    @Override
    public Class<?> getBeanType() {
        return beanConfigBeanDeclaration.getBeanType();
    }

    @Override
    public String getBeanName() {
        return beanConfigBeanDeclaration.getBeanName();
    }

    @Override
    public String toString() {
        return beanConfigBeanDeclaration.toString();
    }

    @Override
    public BeanDeclaration get(int index) {
        return getBeanDeclarations().get(index);
    }

    @Override
    public int size() {
        return getBeanDeclarations().size();
    }

    private List<BeanDeclaration> getBeanDeclarations() {
        if (beanDeclarations == null) {
            List<BeanDeclaration> beanDeclarations = new ArrayList<>();

            Class<?> beanConfigBeanType = getBeanType();
            Method[] beanFactoryMethods = beanConfigBeanType.getMethods();
            for (Method beanFactoryMethod : beanFactoryMethods) {
                if (beanFactoryMethod.getDeclaringClass().equals(Object.class)) {
                    continue;
                }

                Class<?> beanDeclarationType = beanFactoryMethod.getReturnType();

                BeanConfigBeanDeclarationLocation location = new BeanConfigBeanDeclarationLocation(this);
                BeanDeclaration beanFactoryMethodBeanDeclaration = new BeanFactoryMethodBeanDeclaration(beanDeclarationType, beanFactoryMethod, location);
                beanDeclarations.add(beanFactoryMethodBeanDeclaration);

            }

            this.beanDeclarations = beanDeclarations;
        }

        return beanDeclarations;
    }

}