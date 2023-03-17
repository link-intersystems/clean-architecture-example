package com.link_intersystems.ioc.declaration;

import java.util.List;

public interface BeanAmbiguityResolver {

    BeanDeclaration selectBean(Class<?> requestedType, List<BeanDeclaration> options);
}
