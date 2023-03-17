package com.link_intersystems.ioc.declaration;

import java.util.List;

public class QualifierBeanAmbiguityResolver implements BeanAmbiguityResolver {

    private String qualifier;

    public QualifierBeanAmbiguityResolver(String qualifier) {
        this.qualifier = qualifier;
    }

    @Override
    public BeanDeclaration selectBean(Class<?> requestedType, String requestedName, List<BeanDeclaration> options) {
        if (qualifier == null) {
            return null;
        }
        return options.stream()
                .filter(bd -> bd.getBeanName()
                        .equals(qualifier)).findFirst().orElse(null);
    }
}
