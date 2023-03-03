package com.link_intersystems.app.context;

import java.util.*;

import static java.util.Objects.*;

public class ApplicationContext implements BeanFactory {

    private Map<BeanDefinition, Object> beansByBeanDefinition = new HashMap<>();
    private Map<BeanDefinition, List<DefaultLazyBeanSetter>> lazyBeanSetters = new HashMap<>();

    private ThreadLocal<Stack<BeanDeclaration>> callStackHolder = new ThreadLocal<>() {
        @Override
        protected Stack<BeanDeclaration> initialValue() {
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

        BeanDeclaration beanDeclaration = new BeanDeclaration(type, name);
        Stack<BeanDeclaration> callStack = callStackHolder.get();

        if (callStack.contains(beanDeclaration)) {
            StringBuilder sb = ExceptionUtils.cyclicExceptionString(beanDeclaration, callStack);
            throw new RuntimeException("Cyclic bean dependency " + beanDeclaration + "\n" + sb);
        }

        callStack.push(beanDeclaration);

        try {
            return tryGetBean(beanDeclaration);
        } catch (Exception e) {
            throw new RuntimeException("Unable to getBean(" + beanDeclaration.getType().getName() + ", " + beanDeclaration.getName() + ")", e);
        } finally {
            callStack.pop();
        }
    }


    private <T> T tryGetBean(BeanDeclaration beanDeclaration) {
        BeanDefinition matchingBeanDefinition = getBeanDefinitionRegitry().getBeanDefinition(beanDeclaration);

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
        BeanDeclaration beanDeclaration = new BeanDeclaration(type, null);
        BeanDefinition beanDefinition = beanDefinitionRegitry.getBeanDefinition(beanDeclaration);

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
        BeanDeclaration beanDeclaration = new BeanDeclaration(type, null);
        return new BeanSelector<T>() {

            @Override
            public T select(String beanName) {
                try {
                    BeanDefinition beanDefinition = beanDefinitionRegitry.getBeanDefinition(beanDeclaration);
                    return (T) beansByBeanDefinition.get(beanDefinition);
                } catch (AmbiguousBeanException e) {
                    List<BeanDefinition> options = e.getOptions();
                    for (BeanDefinition option : options) {
                        BeanDeclaration optionBeanDeclaration = option.getBeanRef();
                        String optionName = optionBeanDeclaration.getName();
                        if (beanName.equals(optionName)) {
                            BeanDefinition beanDefinition = beanDefinitionRegitry.getBeanDefinition(optionBeanDeclaration);
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

