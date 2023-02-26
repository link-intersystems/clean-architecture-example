package com.link_intersystems.app.context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.BiFunction;

public class ApplicationContext implements BeanFactory {

    List<BeanDefinition> simpleBeanDefinitions;

    private Map<BeanDefinition, Object> beansByBeanDefinition = new HashMap<>();

    private ThreadLocal<Stack<BeanRef>> callStackHolder = new ThreadLocal<>() {
        @Override
        protected Stack<BeanRef> initialValue() {
            return new Stack<>();
        }
    };

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
            return tryGetBean(type, name);
        } catch (Exception e) {
            throw new RuntimeException("Unable to getBean(" + beanRef.getType().getName() + ", " + beanRef.getName() + ")", e);
        } finally {
            callStack.pop();
        }
    }


    private <T> T tryGetBean(Class<T> type, String name) {
        List<BeanDefinition> beanDefinitions = getBeanDefinitions();

        List<BeanDefinition> matchingBeanDefinitions = new ArrayList<>();

        for (BeanDefinition beanDefinition : beanDefinitions) {
            if (!beanDefinition.isInstance(type)) {
                continue;
            }

            if (name != null && !beanDefinition.isNamed(name)) {
                continue;
            }


            matchingBeanDefinitions.add(beanDefinition);
        }


        if (matchingBeanDefinitions.isEmpty()) {
            throw new RuntimeException("Bean " + type.getName() + " is not available.");
        }

        if (matchingBeanDefinitions.size() == 1) {
            BeanDefinition beanDefinition = matchingBeanDefinitions.get(0);
            Object bean = beansByBeanDefinition.get(beanDefinition);
            if (bean == null) {
                bean = beanDefinition.getBean();
                beansByBeanDefinition.put(beanDefinition, bean);
            }
            return (T) bean;
        }

        throw new RuntimeException("Bean " + type.getName() + " is ambiguous. : " + beanDefinitions);
    }

    private List<BeanDefinition> getBeanDefinitions() {
        if (simpleBeanDefinitions == null) {
            try {
                simpleBeanDefinitions = createBeanDefinitions();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return simpleBeanDefinitions;
    }

    private List<BeanDefinition> createBeanDefinitions() throws IOException, ClassNotFoundException {
        List<BeanDefinition> beanDefinitions = new ArrayList<>();

        List<BeanDefinition> simpleSimpleBeanDefinitions = readBeanDefinitions("META-INF/beans/com.link_intersystems.app.context.Bean", this::createSimpleBeanDefinition);
        beanDefinitions.addAll(simpleSimpleBeanDefinitions);

        List<BeanDefinition> beanConfigBeanDefinitions = readBeanDefinitions(BeanConfig.class, this::createBeanConfigBeanDefinitions);
        beanDefinitions.addAll(beanConfigBeanDefinitions);

        return beanDefinitions;
    }

    private List<BeanDefinition> createSimpleBeanDefinition(URL resource, Class<?> beanType) {
        return Collections.singletonList(new SimpleBeanDefinition(resource, beanType, ApplicationContext.this));
    }

    private List<BeanDefinition> createBeanConfigBeanDefinitions(URL resource, Class<?> beanConfigType) {
        List<BeanDefinition> beanDefinitions = new ArrayList<>();
        beanDefinitions.addAll(createSimpleBeanDefinition(resource, beanConfigType));
        List<BeanDefinition> beanConfigBeanDefinitions = new BeanConfig(resource, beanConfigType, beanConfigType, this).getBeanDefinitions();
        beanDefinitions.addAll(beanConfigBeanDefinitions);

        return beanDefinitions;
    }

    private List<BeanDefinition> readBeanDefinitions(Class<?> type, BiFunction<URL, Class<?>, List<BeanDefinition>> beanDefintionFactory) throws IOException, ClassNotFoundException {
        return readBeanDefinitions("META-INF/beans/" + type.getName(), beanDefintionFactory);
    }

    private List<BeanDefinition> readBeanDefinitions(String resource, BiFunction<URL, Class<?>, List<BeanDefinition>> beanDefintionFactory) throws IOException, ClassNotFoundException {
        List<BeanDefinition> beanDefinitions = new ArrayList<>();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(resource);

        while (resources.hasMoreElements()) {
            URL nextResource = resources.nextElement();
            List<String> lines = readLines(nextResource);

            for (String line : lines) {
                Class<?> type = Class.forName(line);
                List<BeanDefinition> providedBeanDefinitions = beanDefintionFactory.apply(nextResource, type);
                beanDefinitions.addAll(providedBeanDefinitions);
            }
        }

        return beanDefinitions;
    }

    private List<String> readLines(URL resource) throws IOException {
        List<String> lines = new ArrayList<>();

        try (InputStream inputStream = resource.openConnection().getInputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                int commentChar = line.indexOf("#");
                if (commentChar > -1) {
                    line = line.substring(0, commentChar);
                }

                line = line.trim();

                lines.add(line);
            }
        }


        return lines;
    }

}

