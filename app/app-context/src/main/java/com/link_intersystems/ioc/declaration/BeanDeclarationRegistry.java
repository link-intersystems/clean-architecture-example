package com.link_intersystems.ioc.declaration;

import com.link_intersystems.ioc.context.AmbiguousBeanException;
import com.link_intersystems.ioc.declaration.locator.BeanConfigSupportBeanDeclarationLocator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Objects.*;

public class BeanDeclarationRegistry {

    public static final Predicate<BeanDeclaration> DEFAULT_BEAN_DEFINITION_EXCLUDE_FILTER = bd -> false;
    private BeanDeclarationLocator beanDeclarationLocator;
    private List<BeanDeclaration> beanDeclarations;

    private Predicate<BeanDeclaration> beanDeclarationExcludeFilter = DEFAULT_BEAN_DEFINITION_EXCLUDE_FILTER;

    public BeanDeclarationRegistry() {
        this(new BeanConfigSupportBeanDeclarationLocator());
    }

    public BeanDeclarationRegistry(BeanDeclarationLocator beanDeclarationLocator) {
        this.beanDeclarationLocator = requireNonNull(beanDeclarationLocator);
    }

    public void setBeanDeclarationExcludeFilter(Predicate<BeanDeclaration> beanDeclarationExcludeFilter) {
        this.beanDeclarationExcludeFilter = beanDeclarationExcludeFilter == null ? DEFAULT_BEAN_DEFINITION_EXCLUDE_FILTER : beanDeclarationExcludeFilter;
    }

    public BeanDeclaration getBeanDeclaration(Class<?> type, String name) {
        List<BeanDeclaration> beanDeclarations = getBeanDeclaration(type);
        return beanDeclarations.stream().filter(bd -> {
            if (name == null) {
                return true;
            }
            return bd.getBeanName().equals(name);
        }).findFirst().orElseThrow(() -> new IllegalStateException("No bean declaration of " + type + " (" + name + ") found."));
    }

    public BeanDeclaration getExactBeanDeclaration(Class<?> type) {
        List<BeanDeclaration> beanDeclarations = getBeanDeclaration(type);

        switch (beanDeclarations.size()) {
            case 0:
                return null;
            case 1:
                return beanDeclarations.get(0);
            default:
                throw new AmbiguousBeanException("Multiple matching beans of " + type, beanDeclarations);
        }
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
        if (beanDeclarations == null) {
            beanDeclarations = beanDeclarationLocator.getBeanDeclarations();
        }
        return beanDeclarations;
    }


}
