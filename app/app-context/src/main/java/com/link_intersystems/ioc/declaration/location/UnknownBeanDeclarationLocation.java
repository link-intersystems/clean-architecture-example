package com.link_intersystems.ioc.declaration.location;

import com.link_intersystems.ioc.declaration.BeanDeclarationLocation;

public class UnknownBeanDeclarationLocation implements BeanDeclarationLocation {

    public static final UnknownBeanDeclarationLocation INSTANCE = new UnknownBeanDeclarationLocation();

    UnknownBeanDeclarationLocation(){
    }

    @Override
    public String toString() {
        return "???";
    }
}
