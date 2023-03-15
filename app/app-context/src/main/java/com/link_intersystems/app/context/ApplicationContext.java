package com.link_intersystems.app.context;

import java.util.*;

import static java.util.Objects.*;

public class ApplicationContext implements BeanFactory {

    private Map<BeanDefinition, Object> beansByBeanDefinition = new HashMap<>();
    private Map<BeanDefinition, List<DefaultLazyBeanSetter>> lazyBeanSetters = new HashMap<>();

    private ThreadLocal<Stack<BeanId>> callStackHolder = new ThreadLocal<>() {
        @Override
        protected Stack<BeanId> initialValue() {
            return new Stack<>();
        }
    };
    private BeanDefinitionRegitry beanDefinitionRegitry;

    public ApplicationContext() {
        this(new BeanDefinitionRegitry(new MetaInfBeanDefinitionLocator()));
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

        BeanId beanId = new BeanId(type, name);
        Stack<BeanId> callStack = callStackHolder.get();

        if (callStack.contains(beanId)) {
            StringBuilder sb = ExceptionUtils.cyclicExceptionString(beanId, callStack);
            throw new RuntimeException("Cyclic bean dependency " + beanId + "\n" + sb);
        }

        callStack.push(beanId);

        try {
            return tryGetBean(beanId);
        } catch (Exception e) {
            throw new RuntimeException("Unable to getBean(" + beanId.getType().getName() + ", " + beanId.getName() + ")", e);
        } finally {
            callStack.pop();
        }
    }

    @Override
    public <T> List<T> getBeans(Class<T> type) {
        List<T> beans = new ArrayList<>();
        BeanId beanId = new BeanId(type, null);
        List<BeanDefinition> beanDefinitions = getBeanDefinitionRegitry().getBeanDefinitions(beanId);

        for (BeanDefinition beanDefinition : beanDefinitions) {
            BeanDeclaration actBeanDeclaration = beanDefinition.getBeanDeclaration();
            BeanId id = actBeanDeclaration.getId();
            Object bean = tryGetBean(id);
            beans.add((T) bean);
        }

        return beans;
    }

    private <T> T tryGetBean(BeanId beanId) {
        BeanDefinition matchingBeanDefinition = getBeanDefinitionRegitry().getBeanDefinition(beanId);

        return getBean(matchingBeanDefinition);
    }

    private <T> T getBean(BeanDefinition beanDefinition) {
        Object bean = beansByBeanDefinition.get(beanDefinition);

        if (bean == null) {
            bean = beanDefinition.createBean(this);
            registerBean(beanDefinition, bean);
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
        BeanId beanId = new BeanId(type, null);
        BeanDefinition beanDefinition = beanDefinitionRegitry.getBeanDefinition(beanId);

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

    @Override
    public <T> BeanSelector<T> getBeanSelector(Class<T> type) {
        BeanId beanId = new BeanId(type, null);
        return new BeanSelector<T>() {

            @Override
            public T select(String beanName) {
                try {
                    BeanDefinition beanDefinition = beanDefinitionRegitry.getBeanDefinition(beanId);
                    return (T) beansByBeanDefinition.get(beanDefinition);
                } catch (AmbiguousBeanException e) {
                    List<BeanDefinition> options = e.getOptions();
                    for (BeanDefinition option : options) {
                        BeanDeclaration optionBeanDeclaration = option.getBeanDeclaration();
                        BeanId optionBeanId = optionBeanDeclaration.getId();
                        String optionName = optionBeanId.getName();
                        if (beanName.equals(optionName)) {
                            BeanDefinition beanDefinition = beanDefinitionRegitry.getBeanDefinition(optionBeanId);
                            return getBean(beanDefinition);
                        }
                    }
                    StringBuilder sb = new StringBuilder("No bean ");
                    sb.append(type.getName());
                    sb.append(" named ");
                    sb.append(beanName);
                    sb.append(" available. Available beans are:\n");

                    Iterator<BeanDefinition> iterator = options.iterator();
                    while (iterator.hasNext()) {
                        BeanDefinition option = iterator.next();
                        sb.append("\t o ");
                        sb.append(option);
                        if (iterator.hasNext()) {
                            sb.append("\n");
                        }
                    }
                    throw new RuntimeException(sb.toString());
                }
            }
        };
    }
}

