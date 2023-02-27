package com.link_intersystems.app.context;

import java.util.*;

import static java.util.Objects.*;

public class ApplicationContext implements BeanFactory {

    private Map<BeanDefinition, Object> beansByBeanDefinition = new HashMap<>();
    private Map<BeanDefinition, List<DefaultLazyBeanSetter>> lazyBeanSetters = new HashMap<>();

    private ThreadLocal<Stack<BeanRef>> callStackHolder = new ThreadLocal<>() {
        @Override
        protected Stack<BeanRef> initialValue() {
            return new Stack<>();
        }
    };
    private BeanDefinitionRegitry beanDefinitionRegitry;

    public ApplicationContext() {
        this(new BeanDefinitionRegitry(new DefaultBeanDefinitionLocator()));
    }

    public ApplicationContext(BeanDefinitionRegitry beanDefinitionRegitry) {
        this.beanDefinitionRegitry = requireNonNull(beanDefinitionRegitry);
    }

    public BeanDefinitionRegitry getBeanDefinitionRegitry() {
        return beanDefinitionRegitry;
    }

    @Override
    public <T> T getBean(Class<T> type, String name) {
        if (BeanFactory.class.equals(type)) {
            return (T) this;
        }

        BeanRef beanRef = new BeanRef(type, name);
        Stack<BeanRef> callStack = callStackHolder.get();

        if (callStack.contains(beanRef)) {
            StringBuilder sb = ExceptionUtils.cyclicExceptionString(beanRef, callStack);
            throw new RuntimeException("Cyclic bean dependency " + beanRef + "\n" + sb);
        }

        callStack.push(beanRef);

        try {
            return tryGetBean(beanRef);
        } catch (Exception e) {
            throw new RuntimeException("Unable to getBean(" + beanRef.getType().getName() + ", " + beanRef.getName() + ")", e);
        } finally {
            callStack.pop();
        }
    }


    private <T> T tryGetBean(BeanRef beanRef) {
        BeanDefinition matchingBeanDefinition = getBeanDefinitionRegitry().getBeanDefinition(beanRef);

        Object bean = beansByBeanDefinition.get(matchingBeanDefinition);

        if (bean == null) {
            bean = matchingBeanDefinition.createBean(this);
            registerBean(matchingBeanDefinition, bean);
        }

        return (T) bean;
    }

    private void registerBean(BeanDefinition beanDefinition, Object bean) {
        beansByBeanDefinition.put(beanDefinition, bean);

        List<DefaultLazyBeanSetter> lazyBeanSettersList = lazyBeanSetters.remove(beanDefinition);
        if (lazyBeanSettersList != null) {
            lazyBeanSettersList.forEach(lbs -> lbs.setBean(bean));
        }
    }

    @Override
    public <T> LazyBeanSetter<T> getLazyBeanSetter(Class<T> type) {
        BeanRef beanRef = new BeanRef(type, null);
        BeanDefinition beanDefinition = beanDefinitionRegitry.getBeanDefinition(beanRef);

        DefaultLazyBeanSetter<T> lazyBeanSetter = new DefaultLazyBeanSetter<>();
        Object bean = beansByBeanDefinition.get(beanDefinition);

        if (bean == null) {
            List<DefaultLazyBeanSetter> lazyBeanSettersList = lazyBeanSetters.computeIfAbsent(beanDefinition, bd -> new ArrayList<>());
            lazyBeanSettersList.add(lazyBeanSetter);
        } else {
            lazyBeanSetter.setBean((T) bean);
        }

        return lazyBeanSetter;
    }

}

