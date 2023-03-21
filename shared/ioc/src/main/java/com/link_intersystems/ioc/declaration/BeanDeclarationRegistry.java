package com.link_intersystems.ioc.declaration;

import com.link_intersystems.ioc.declaration.config.AnnotationBeanConfigDetector;
import com.link_intersystems.ioc.declaration.config.CompositeBeanConfigDetector;
import com.link_intersystems.ioc.declaration.config.NamePatternBeanConfigDetector;
import com.link_intersystems.ioc.declaration.locator.MetaInfBeanDeclarationLocator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Objects.*;

public class BeanDeclarationRegistry {

    public static final Predicate<BeanDeclaration> DEFAULT_BEAN_DEFINITION_EXCLUDE_FILTER = bd -> false;
    private BeanDeclarationLocator beanDeclarationLocator;
    private List<BeanDeclaration> beanDeclarations;

    private Predicate<BeanDeclaration> beanDeclarationExcludeFilter = DEFAULT_BEAN_DEFINITION_EXCLUDE_FILTER;
    private BeanAmbiguityResolver beanAmbiguityResolver;
    private BeanConfigDetector beanConfigDetector = new CompositeBeanConfigDetector(new AnnotationBeanConfigDetector(), new NamePatternBeanConfigDetector());

    public BeanDeclarationRegistry() {
        this(new MetaInfBeanDeclarationLocator());
    }

    public BeanDeclarationRegistry(BeanDeclarationLocator beanDeclarationLocator) {
        this.beanDeclarationLocator = requireNonNull(beanDeclarationLocator);
    }

    public void setBeanDeclarationExcludeFilter(Predicate<BeanDeclaration> beanDeclarationExcludeFilter) {
        this.beanDeclarationExcludeFilter = beanDeclarationExcludeFilter == null ? DEFAULT_BEAN_DEFINITION_EXCLUDE_FILTER : beanDeclarationExcludeFilter;
    }

    public void setBeanAmbiguityResolver(BeanAmbiguityResolver beanAmbiguityResolver) {
        this.beanAmbiguityResolver = beanAmbiguityResolver;
    }

    public BeanDeclaration getBeanDeclaration(Class<?> type, String name) throws AmbiguousBeanException {
        List<BeanDeclaration> matchingBeanDeclarations = getBeanDeclaration(type);
        return selectBean(type, name, matchingBeanDeclarations);
    }

    private BeanDeclaration selectBean(Class<?> requestedType, String requestedName, List<BeanDeclaration> matchingBeanDeclarations) {
        BeanDeclaration selectedBeanDeclaration;

        switch (matchingBeanDeclarations.size()) {
            case 0:
                throw new IllegalStateException("No bean declaration of " + requestedType + " (" + requestedName + ") found.");
            case 1:
                selectedBeanDeclaration = matchingBeanDeclarations.get(0);
                break;
            default:
                selectedBeanDeclaration = handleAmbiguousBeanDeclaration(requestedType, requestedName, matchingBeanDeclarations);
        }

        return selectedBeanDeclaration;
    }

    private BeanDeclaration handleAmbiguousBeanDeclaration(Class<?> requestedType, String requestedName, List<BeanDeclaration> matchingBeanDeclarations) {
        BeanAmbiguityResolver beanAmbiguityResolver = getBeanAmbiguityResolver(requestedName);

        BeanDeclaration selectedBeanDeclaration = beanAmbiguityResolver.selectBean(requestedType, matchingBeanDeclarations);

        if (selectedBeanDeclaration == null) {
            throw new AmbiguousBeanException("Ambiguous beans of " + requestedType + " (" + requestedName + ") found.", matchingBeanDeclarations);
        }

        return selectedBeanDeclaration;
    }

    private BeanAmbiguityResolverChain getBeanAmbiguityResolver(String requestedName) {
        List<BeanAmbiguityResolver> beanAmbiguityResolvers = new ArrayList<>();

        beanAmbiguityResolvers.add(new QualifierBeanAmbiguityResolver(requestedName));

        if (beanAmbiguityResolver != null) {
            beanAmbiguityResolvers.add(beanAmbiguityResolver);
        }

        return new BeanAmbiguityResolverChain(beanAmbiguityResolvers);
    }


    public BeanDeclaration getExactBeanDeclaration(Class<?> type) throws AmbiguousBeanException {
        List<BeanDeclaration> beanDeclarations = getBeanDeclaration(type);
        return selectBean(type, null, beanDeclarations);
    }

    public List<BeanDeclaration> getBeanDeclaration(Class<?> type) {
        List<BeanDeclaration> beanDeclarations = new ArrayList<>();

        for (BeanDeclaration beanDeclaration : getBeanDeclarations()) {
            Class<?> beanType = beanDeclaration.getBeanType();
            if (type.isAssignableFrom(beanType)) {
                beanDeclarations.add(beanDeclaration);
            }
        }

        return beanDeclarations;
    }

    public List<BeanDeclaration> getBeanDeclarations() {
        if (beanDeclarations == null) {
            beanDeclarations = beanDeclarationLocator.getBeanDeclarations()
                    .stream()
                    .filter(beanDeclarationExcludeFilter.negate())
                    .collect(Collectors.toList());

            processBeanConfigs(beanDeclarations);
        }
        return beanDeclarations;
    }

    private void processBeanConfigs(List<BeanDeclaration> beanDeclarations) {
        for (int i = 0; i < beanDeclarations.size(); i++) {
            BeanDeclaration beanDeclaration = beanDeclarations.get(i);
            if (beanConfigDetector.isBeanConfig(beanDeclaration)) {
                BeanConfigBeanDeclaration beanConfig = new BeanConfigBeanDeclaration(beanDeclaration);
                beanDeclarations.set(i, beanConfig);
                beanDeclarations.addAll(i + 1, beanConfig);
            }
        }
    }
}
