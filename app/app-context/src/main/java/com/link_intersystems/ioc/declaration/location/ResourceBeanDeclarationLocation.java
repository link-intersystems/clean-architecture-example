package com.link_intersystems.ioc.declaration.location;

import com.link_intersystems.ioc.declaration.BeanDeclarationLocation;

import java.net.URL;

import static java.util.Objects.*;

public class ResourceBeanDeclarationLocation implements BeanDeclarationLocation {

    private URL resource;

    public ResourceBeanDeclarationLocation(URL resource) {
        this.resource = requireNonNull(resource);
    }

    @Override
    public String toString() {
        return resource.getPath();
    }

    public URL getUrl() {
        return resource;
    }
}
