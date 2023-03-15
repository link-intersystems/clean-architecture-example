package com.link_intersystems.ioc.declaration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Objects.*;

public class BeanDeclarationRegistry {

    public static final Predicate<BeanDeclaration> DEFAULT_BEAN_DEFINITION_EXCLUDE_FILTER = bd -> false;
    private BeanDeclarationLocator beanDeclarationLocator;
    private List<BeanDeclaration> beanDefinitions;

    private Predicate<BeanDeclaration> beanDeclarationExcludeFilter = DEFAULT_BEAN_DEFINITION_EXCLUDE_FILTER;

    public BeanDeclarationRegistry() {
        this(new MetaInfBeanDeclarationLocator());
    }

    public BeanDeclarationRegistry(BeanDeclarationLocator beanDeclarationLocator) {
        this.beanDeclarationLocator = requireNonNull(beanDeclarationLocator);
    }

    public void setBeanDeclarationExcludeFilter(Predicate<BeanDeclaration> beanDeclarationExcludeFilter) {
        this.beanDeclarationExcludeFilter = beanDeclarationExcludeFilter == null ? DEFAULT_BEAN_DEFINITION_EXCLUDE_FILTER : beanDeclarationExcludeFilter;
    }

    public List<BeanDeclaration> getBeanDeclaration(Class<?> type) {
        List<BeanDeclaration> beanDeclarations = new ArrayList<>();

        for (BeanDeclaration beanDeclaration : getBeanDeclarations()) {
            if (beanDeclarationExcludeFilter.test(beanDeclaration)) {
                continue;
            }

            Class<?> beanType = beanDeclaration.getBeanType();
            if (type.isAssignableFrom(beanType)) {
                beanDeclarations.add(beanDeclaration);
            }
        }

        return beanDeclarations;
    }


    private List<BeanDeclaration> getBeanDeclarations() {
        if (beanDefinitions == null) {
            beanDefinitions = beanDeclarationLocator.getBeanDeclarations();
        }
        return beanDefinitions;
    }
}
