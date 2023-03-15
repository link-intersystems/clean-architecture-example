package com.link_intersystems.ioc.context;

import com.link_intersystems.ioc.api.BeanSelector;
import com.link_intersystems.ioc.api.LazyBeanSetter;
import com.link_intersystems.ioc.declaration.BeanDeclaration;
import com.link_intersystems.ioc.declaration.BeanDeclarationRegistry;
import com.link_intersystems.ioc.declaration.BeanFactoryMethodBeanDeclaration;
import com.link_intersystems.ioc.definition.BeanConstructor;
import com.link_intersystems.ioc.definition.BeanId;
import com.link_intersystems.ioc.definition.DefaultBeanConstructor;
import com.link_intersystems.ioc.definition.FactoryMethodBeanConstructor;

import java.util.*;

import static java.util.Objects.*;

public class ApplicationContext implements BeanFactory {

    private Map<BeanDeclaration, Object> beansByDeclaration = new HashMap<>();
    private Map<BeanDeclaration, List<DefaultLazyBeanSetter>> lazyBeanSetters = new HashMap<>();

    private ThreadLocal<Stack<BeanId>> callStackHolder = new ThreadLocal<>() {
        @Override
        protected Stack<BeanId> initialValue() {
            return new Stack<>();
        }
    };
    private BeanDeclarationRegistry beanDeclarationRegistry;

    public ApplicationContext() {
        this(new BeanDeclarationRegistry());
    }

    public ApplicationContext(BeanDeclarationRegistry beanDeclarationRegistry) {
        this.beanDeclarationRegistry = requireNonNull(beanDeclarationRegistry);
    }

    public BeanDeclarationRegistry getBeanDeclarationRegistry() {
        return beanDeclarationRegistry;
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

        Class<?> beanType = beanId.getType();
        String beanName = beanId.getName();
        try {
            BeanDeclaration beanDeclaration = beanDeclarationRegistry.getBeanDeclaration(beanType, beanName);
            return tryGetBean(beanDeclaration);
        } catch (Exception e) {
            throw new RuntimeException("Unable to getBean(" + beanType.getName() + ", " + beanName + ")", e);
        } finally {
            callStack.pop();
        }
    }

    @Override
    public <T> List<T> getBeans(Class<T> type) {
        List<T> beans = new ArrayList<>();
        BeanId beanId = new BeanId(type, null);
        List<BeanDeclaration> beanDeclarations = getBeanDeclarationRegistry().getBeanDeclaration(beanId.getType());

        try {
            for (BeanDeclaration beanDeclaration : beanDeclarations) {
                T bean = tryGetBean(beanDeclaration);
                beans.add(bean);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return beans;
    }

    private <T> T tryGetBean(BeanDeclaration beanDeclaration) throws Exception {
        Object bean = beansByDeclaration.get(beanDeclaration);

        if (bean == null) {
            bean = createBean(beanDeclaration);
            beansByDeclaration.put(beanDeclaration, bean);
            afterBeanCreated(beanDeclaration, bean);
        }

        return (T) bean;
    }

    private void afterBeanCreated(BeanDeclaration beanDeclaration, Object bean) {
        List<DefaultLazyBeanSetter> lazyBeanSettersList = lazyBeanSetters.remove(beanDeclaration);
        if (lazyBeanSettersList != null) {
            lazyBeanSettersList.forEach(lbs -> lbs.setBean(bean));
        }
    }

    private Object createBean(BeanDeclaration beanDeclaration) throws Exception {
        BeanConstructor beanConstructor = resolveBeanConstructor(beanDeclaration);
        return beanConstructor.createBean(this);
    }

    private BeanConstructor resolveBeanConstructor(BeanDeclaration beanDeclaration) {
        if (beanDeclaration instanceof BeanFactoryMethodBeanDeclaration) {
            BeanFactoryMethodBeanDeclaration beanFactoryMethodBeanDeclaration = (BeanFactoryMethodBeanDeclaration) beanDeclaration;
            return new FactoryMethodBeanConstructor(beanFactoryMethodBeanDeclaration);
        }

        return new DefaultBeanConstructor(beanDeclaration);
    }

    @Override
    public <T> LazyBeanSetter<T> getLazyBeanSetter(Class<T> type) {
        BeanDeclaration beanDefinition = beanDeclarationRegistry.getExactBeanDeclaration(type);

        DefaultLazyBeanSetter<T> lazyBeanSetter = new DefaultLazyBeanSetter<>();
        Object bean = beansByDeclaration.get(beanDefinition);

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
        return new BeanSelector<T>() {

            @Override
            public T select(String beanName) {
                try {
                    BeanDeclaration beanDefinition = beanDeclarationRegistry.getExactBeanDeclaration(type);
                    return (T) beansByDeclaration.get(beanDefinition);
                } catch (AmbiguousBeanException e) {
                    List<BeanDeclaration> options = e.getOptions();
                    for (BeanDeclaration option : options) {
                        String optionName = option.getBeanName();
                        if (beanName.equals(optionName)) {
                            try {
                                return tryGetBean(option);
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                    StringBuilder sb = new StringBuilder("No bean ");
                    sb.append(type.getName());
                    sb.append(" named ");
                    sb.append(beanName);
                    sb.append(" available. Available beans are:\n");

                    Iterator<BeanDeclaration> iterator = options.iterator();
                    while (iterator.hasNext()) {
                        BeanDeclaration option = iterator.next();
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

