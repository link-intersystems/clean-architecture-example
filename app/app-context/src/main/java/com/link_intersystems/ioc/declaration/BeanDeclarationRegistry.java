package com.link_intersystems.ioc.declaration;

import com.link_intersystems.ioc.declaration.locator.BeanConfigSupportBeanDeclarationLocator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Objects.*;

public class BeanDeclarationRegistry {

    public static final Predicate<BeanDeclaration> DEFAULT_BEAN_DEFINITION_EXCLUDE_FILTER = bd -> false;
    public static final BeanAmbiguityResolver DEFAULT_BEAN_AMBIGUITY_RESOLVER = (t, n, o) -> null;
    private BeanDeclarationLocator beanDeclarationLocator;
    private List<BeanDeclaration> beanDeclarations;

    private Predicate<BeanDeclaration> beanDeclarationExcludeFilter = DEFAULT_BEAN_DEFINITION_EXCLUDE_FILTER;
    private BeanAmbiguityResolver beanAmbiguityResolver = DEFAULT_BEAN_AMBIGUITY_RESOLVER;

    public BeanDeclarationRegistry() {
        this(new BeanConfigSupportBeanDeclarationLocator());
    }

    public BeanDeclarationRegistry(BeanDeclarationLocator beanDeclarationLocator) {
        this.beanDeclarationLocator = requireNonNull(beanDeclarationLocator);
    }

    public void setBeanDeclarationExcludeFilter(Predicate<BeanDeclaration> beanDeclarationExcludeFilter) {
        this.beanDeclarationExcludeFilter = beanDeclarationExcludeFilter == null ? DEFAULT_BEAN_DEFINITION_EXCLUDE_FILTER : beanDeclarationExcludeFilter;
    }

    public void setBeanAmbiguityResolver(BeanAmbiguityResolver beanAmbiguityResolver) {
        this.beanAmbiguityResolver = beanAmbiguityResolver == null ? DEFAULT_BEAN_AMBIGUITY_RESOLVER : beanAmbiguityResolver;
    }

    public BeanDeclaration getBeanDeclaration(Class<?> type, String name) throws AmbiguousBeanException {
        List<BeanDeclaration> beanDeclarations = getBeanDeclaration(type);

        List<BeanDeclaration> matchingBeanDeclarations = beanDeclarations.stream().filter(bd -> {
            if (name == null) {
                return true;
            }
            return bd.getBeanName().equals(name);
        }).collect(Collectors.toList());

        return selectBean(type, name, matchingBeanDeclarations);
    }

    private BeanDeclaration selectBean(Class<?> type, String name, List<BeanDeclaration> matchingBeanDeclarations) {
        switch (matchingBeanDeclarations.size()) {
            case 0:
                throw new IllegalStateException("No bean declaration of " + type + " (" + name + ") found.");
            case 1:
                return matchingBeanDeclarations.get(0);
            default:
                return handleBeanAmbiguity(type, name, matchingBeanDeclarations);
        }
    }

    private BeanDeclaration handleBeanAmbiguity(Class<?> requestedType, String requestedName, List<BeanDeclaration> matchingBeanDeclarations) throws AmbiguousBeanException {
        BeanDeclaration selectedBeanDeclaration = beanAmbiguityResolver.selectBean(requestedType, requestedName, matchingBeanDeclarations);

        if (selectedBeanDeclaration == null) {
            throw new AmbiguousBeanException("Ambiguous beans of " + requestedType + " (" + requestedName + ") found.", matchingBeanDeclarations);
        }

        return selectedBeanDeclaration;
    }

    public BeanDeclaration getExactBeanDeclaration(Class<?> type) throws AmbiguousBeanException {
        List<BeanDeclaration> beanDeclarations = getBeanDeclaration(type);
        return selectBean(type, null, beanDeclarations);
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
