package com.link_intersystems.app.context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Objects.*;

public class BeanDefinitionRegitry {

    private BeanDefinitionLocator beanDefinitionLocator;
    private List<BeanDefinition> beanDefinitions;

    private Predicate<BeanDefinition> beanDefinitionFilter = bd -> true;

    public BeanDefinitionRegitry() {
        this(new DefaultBeanDefinitionLocator());
    }

    public BeanDefinitionRegitry(BeanDefinitionLocator beanDefinitionLocator) {
        this.beanDefinitionLocator = requireNonNull(beanDefinitionLocator);
    }

    public void setBeanDefinitionFilter(Predicate<BeanDefinition> beanDefinitionFilter) {
        this.beanDefinitionFilter = beanDefinitionFilter == null ? bd -> true : beanDefinitionFilter;
    }

    public BeanDefinition getBeanDefinition(BeanRef beanRef) {
        List<BeanDefinition> beanDefinitions = getBeanDefinitions();

        List<BeanDefinition> matchingBeanDefinitions = new ArrayList<>();

        for (BeanDefinition beanDefinition : beanDefinitions) {
            if (!beanDefinitionFilter.test(beanDefinition)) {
                continue;
            }

            BeanRef actualBeanRef = beanDefinition.getBeanRef();

            if (!matches(beanRef, actualBeanRef)) {
                continue;
            }


            matchingBeanDefinitions.add(beanDefinition);
        }

        if (matchingBeanDefinitions.isEmpty()) {
            throw new RuntimeException("Bean " + beanRef.getType().getName() + " is not available.");
        }

        if (matchingBeanDefinitions.size() == 1) {
            BeanDefinition beanDefinition = matchingBeanDefinitions.get(0);
            return beanDefinition;
        }


        throw new RuntimeException(ExceptionUtils.ambiguousBean(beanRef, matchingBeanDefinitions));
    }

    protected boolean matches(BeanRef actBeanRef, BeanRef otherBeanRef) {
        if (isInstance(actBeanRef, otherBeanRef)) {
            return true;
        }

        return isNamed(actBeanRef, otherBeanRef.getName());
    }

    private boolean isInstance(BeanRef actBeanRef, BeanRef otherBeanRef) {
        return actBeanRef.getType().isAssignableFrom(otherBeanRef.getType());
    }


    private boolean isNamed(BeanRef actBeanRef, String name) {
        String actualName = actBeanRef.getName();
        if (actualName != null && actualName.equals(name)) {
            return true;
        }

        return actBeanRef.getType().getSimpleName().equals(name);
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
