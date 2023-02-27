package com.link_intersystems.app.context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.function.BiFunction;

public class DefaultBeanDefinitionLocator implements BeanDefinitionLocator {
    @Override
    public List<BeanDefinition> getBeanDefinitions() throws IOException, ClassNotFoundException {
        List<BeanDefinition> beanDefinitions = new ArrayList<>();

        List<BeanDefinition> simpleSimpleBeanDefinitions = readBeanDefinitions("META-INF/beans/com.link_intersystems.app.context.Bean", this::createSimpleBeanDefinition);
        beanDefinitions.addAll(simpleSimpleBeanDefinitions);

        List<BeanDefinition> beanConfigBeanDefinitions = readBeanDefinitions(BeanConfig.class, this::createBeanConfigBeanDefinitions);
        beanDefinitions.addAll(beanConfigBeanDefinitions);

        return beanDefinitions;
    }

    private List<BeanDefinition> createSimpleBeanDefinition(URL resource, Class<?> beanType) {
        BeanRef beanRef = new BeanRef(beanType, beanType.getName());
        return Collections.singletonList(new SimpleBeanDefinition(resource, beanRef));
    }

    private List<BeanDefinition> createBeanConfigBeanDefinitions(URL resource, Class<?> beanConfigType) {
        List<BeanDefinition> beanDefinitions = new ArrayList<>();
        beanDefinitions.addAll(createSimpleBeanDefinition(resource, beanConfigType));
        List<BeanDefinition> beanConfigBeanDefinitions = new BeanConfig(resource, beanConfigType, beanConfigType).getBeanDefinitions();
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
