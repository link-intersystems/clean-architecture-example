package com.link_intersystems.ioc.definition;

import com.link_intersystems.ioc.context.AmbiguousBeanException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Objects.*;

public class BeanDefinitionRegitry {

    public static final Predicate<BeanDefinition> DEFAULT_BEAN_DEFINITION_EXCLUDE_FILTER = bd -> false;
    private BeanDefinitionLocator beanDefinitionLocator;
    private List<BeanDefinition> beanDefinitions;

    private Predicate<BeanDefinition> beanDefinitionExcludeFilter = DEFAULT_BEAN_DEFINITION_EXCLUDE_FILTER;

    public BeanDefinitionRegitry() {
        this(new MetaInfBeanDefinitionLocator());
    }

    public BeanDefinitionRegitry(BeanDefinitionLocator beanDefinitionLocator) {
        this.beanDefinitionLocator = requireNonNull(beanDefinitionLocator);
    }

    public void setBeanDefinitionExcludeFilter(Predicate<BeanDefinition> beanDefinitionExcludeFilter) {
        this.beanDefinitionExcludeFilter = beanDefinitionExcludeFilter == null ? DEFAULT_BEAN_DEFINITION_EXCLUDE_FILTER : beanDefinitionExcludeFilter;
    }

    public BeanDefinition getBeanDefinition(BeanId beanId) throws AmbiguousBeanException {
        List<BeanDefinition> matchingBeanDefinitions = getBeanDefinitions(beanId);

        if (matchingBeanDefinitions.isEmpty()) {
            throw new RuntimeException("Bean " + beanId.getType().getName() + " is not available.");
        }

        if (matchingBeanDefinitions.size() == 1) {
            BeanDefinition beanDefinition = matchingBeanDefinitions.get(0);
            return beanDefinition;
        }


        throw new AmbiguousBeanException(ExceptionUtils.ambiguousBean(beanId, matchingBeanDefinitions), matchingBeanDefinitions);
    }

    public List<BeanDefinition> getBeanDefinitions(BeanId beanId) {
        List<BeanDefinition> beanDefinitions = getBeanDefinitions();

        List<BeanDefinition> matchingBeanDefinitions = new ArrayList<>();

        for (BeanDefinition beanDefinition : beanDefinitions) {
            if (beanDefinitionExcludeFilter.test(beanDefinition)) {
                continue;
            }

            BeanDeclaration actualBeanDefinition = beanDefinition.getBeanDeclaration();

            if (!isInstance(beanId, actualBeanDefinition.getId())) {
                continue;
            }

            if (beanId.getName() != null) {
                if (!isNamed(actualBeanDefinition.getId(), beanId.getName())) {
                    continue;
                }
            }


            matchingBeanDefinitions.add(beanDefinition);
        }
        return matchingBeanDefinitions;
    }

    private boolean isInstance(BeanId actBeanId, BeanId otherBeanDeclaration) {
        return actBeanId.getType().isAssignableFrom(otherBeanDeclaration.getType());
    }


    private boolean isNamed(BeanId actBeanDeclaration, String name) {
        String actualName = actBeanDeclaration.getName();
        if (actualName != null && actualName.equals(name)) {
            return true;
        }

        return actBeanDeclaration.getType().getSimpleName().equals(name);
    }

    private List<BeanDefinition> getBeanDefinitions() {
        if (beanDefinitions == null) {
            try {
                beanDefinitions = beanDefinitionLocator.getBeanDefinitions();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return beanDefinitions;
    }
}
