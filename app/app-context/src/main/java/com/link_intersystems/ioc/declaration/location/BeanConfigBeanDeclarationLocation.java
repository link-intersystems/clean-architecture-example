package com.link_intersystems.ioc.declaration.location;

import com.link_intersystems.ioc.declaration.BeanDeclaration;
import com.link_intersystems.ioc.declaration.BeanDeclarationLocation;

import static java.util.Objects.*;

public class BeanConfigBeanDeclarationLocation implements BeanDeclarationLocation {

    private BeanDeclaration beanConfigBeanDeclaration;

    public BeanConfigBeanDeclarationLocation(BeanDeclaration beanConfigBeanDeclaration) {
        this.beanConfigBeanDeclaration = requireNonNull(beanConfigBeanDeclaration);
    }

    public BeanDeclaration getBeanDeclaration() {
        return beanConfigBeanDeclaration;
    }
}
