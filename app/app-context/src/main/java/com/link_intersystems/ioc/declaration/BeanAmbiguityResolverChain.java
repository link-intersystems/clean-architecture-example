package com.link_intersystems.ioc.declaration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.*;

public class BeanAmbiguityResolverChain implements BeanAmbiguityResolver {

    private List<BeanAmbiguityResolver> beanAmbiguityResolvers;

    public BeanAmbiguityResolverChain(BeanAmbiguityResolver... beanAmbiguityResolvers) {
        this(Arrays.asList(beanAmbiguityResolvers));
    }

    public BeanAmbiguityResolverChain(List<BeanAmbiguityResolver> beanAmbiguityResolvers) {
        this.beanAmbiguityResolvers = new ArrayList<>(requireNonNull(beanAmbiguityResolvers));
    }

    @Override
    public BeanDeclaration selectBean(Class<?> requestedType, List<BeanDeclaration> options) {
        for (BeanAmbiguityResolver beanAmbiguityResolver : beanAmbiguityResolvers) {
            BeanDeclaration selectedBeanDeclaration = beanAmbiguityResolver.selectBean(requestedType, options);
            if (selectedBeanDeclaration != null) {
                return selectedBeanDeclaration;
            }
        }
        return null;
    }
}
