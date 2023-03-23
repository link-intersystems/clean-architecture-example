package com.link_intersystems.ioc.context;

import com.link_intersystems.ioc.aop.BeanPostProcessor;
import com.link_intersystems.ioc.api.BeanSelector;
import com.link_intersystems.ioc.api.LazyBeanSetter;
import com.link_intersystems.ioc.declaration.BeanConfigBeanDeclaration;
import com.link_intersystems.ioc.declaration.BeanDeclaration;
import com.link_intersystems.ioc.declaration.BeanDeclarationRegistry;
import com.link_intersystems.ioc.definition.BeanDefinition;
import com.link_intersystems.ioc.definition.constructor.DefaultBeanConstructorFactory;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.*;

public class ApplicationContext implements BeanFactory {

    private Map<BeanDeclaration, Object> beansByDeclaration = new HashMap<>();
    private Map<BeanDeclaration, List<DefaultLazyBeanSetter>> lazyBeanSetters = new HashMap<>();

    private ThreadLocal<Stack<BeanDeclaration>> callStackHolder = ThreadLocal.withInitial(() -> new Stack<>());
    private BeanDeclarationRegistry beanDeclarationRegistry;

    private ConstructorArgsResolver constructorArgsResolver = new DefaultConstructorArgsResolver(this);
    private BeanConstructorFactory beanConstructorFactory = new DefaultBeanConstructorFactory();

    private boolean initialized;

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

        if (!initialized) {
            initialized = true;
            init();
        }

        BeanDeclaration beanDeclaration = beanDeclarationRegistry.getBeanDeclaration(type, name);

        Stack<BeanDeclaration> callStack = callStackHolder.get();

        if (callStack.contains(beanDeclaration)) {
            StringBuilder sb = ExceptionUtils.cyclicExceptionString(beanDeclaration, callStack);
            throw new RuntimeException("Cyclic bean dependency " + beanDeclaration + "\n" + sb);
        }

        callStack.push(beanDeclaration);

        try {
            return tryGetBean(beanDeclaration);
        } catch (Exception e) {
            throw new RuntimeException("Unable to getBean(" + type.getName() + ", " + name + ")", e);
        } finally {
            callStack.pop();
        }
    }

    private void init() {
        BeanDeclarationRegistry registry = getBeanDeclarationRegistry();
        List<BeanDeclaration> beanDeclarations = registry.getBeanDeclarations();
        beanDeclarations.forEach(this::getBeanDefinition);
    }

    @Override
    public <T> List<T> getBeans(Class<T> type) {
        List<BeanDefinition<T>> beanDefinitions = getBeanDefinitions(type);

        return beanDefinitions.stream().map(BeanDefinition::getBean).collect(Collectors.toList());
    }

    private <T> T tryGetBean(BeanDeclaration beanDeclaration) throws Exception {
        Object bean = beansByDeclaration.get(beanDeclaration);

        if (bean == null) {
            bean = createBean(beanDeclaration);
            if (!BeanPostProcessor.class.isAssignableFrom(beanDeclaration.getBeanType()) && !(beanDeclaration instanceof BeanConfigBeanDeclaration)) {
                bean = postProcessBean(bean, beanDeclaration);
            }
            beansByDeclaration.put(beanDeclaration, bean);
            afterBeanCreated(beanDeclaration, bean);
        }

        return (T) bean;
    }

    private Object postProcessBean(Object bean, BeanDeclaration beanDeclaration) {
        List<BeanPostProcessor> beanPostProcessors = getBeans(BeanPostProcessor.class);

        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.processBean(this, beanDeclaration, bean);
        }

        return bean;
    }

    private void afterBeanCreated(BeanDeclaration beanDeclaration, Object bean) {
        List<DefaultLazyBeanSetter> lazyBeanSettersList = lazyBeanSetters.remove(beanDeclaration);
        if (lazyBeanSettersList != null) {
            lazyBeanSettersList.forEach(lbs -> lbs.setBean(bean));
        }
    }

    private Object createBean(BeanDeclaration beanDeclaration) throws Exception {
        BeanConstructor beanConstructor = beanConstructorFactory.createBeanConstructor(beanDeclaration, constructorArgsResolver);
        return beanConstructor.createBean(this);
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
        List<BeanDeclaration> beanDeclarations = getBeanDeclarationRegistry().getBeanDeclaration(type);
        return new DefaultBeanSelector<>(type, beanDeclarations, this::getBeanDefinition);
    }

    public <T> List<BeanDefinition<T>> getBeanDefinitions(Class<T> type) {
        List<BeanDeclaration> beanDeclarations = getBeanDeclarationRegistry().getBeanDeclaration(type);

        List<BeanDefinition<T>> beanDefinitions = new ArrayList<>();
        try {
            for (BeanDeclaration beanDeclaration : beanDeclarations) {
                T bean = tryGetBean(beanDeclaration);
                BeanDefinition<T> beanDefinition = new BeanDefinition<>(bean, beanDeclaration);
                beanDefinitions.add(beanDefinition);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return beanDefinitions;
    }

    public <T> BeanDefinition<T> getBeanDefinition(BeanDeclaration beanDeclaration) {

        try {
            T bean = tryGetBean(beanDeclaration);
            return new BeanDefinition<>(bean, beanDeclaration);
        } catch (Exception e) {
            throw new RuntimeException(beanDeclaration.toString(), e);
        }

    }
}

