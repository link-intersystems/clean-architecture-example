package com.link_intersystems.ioc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.BiFunction;

public class MetaInfBeanDefinitionLocator implements BeanDefinitionLocator {
    @Override
    public List<BeanDefinition> getBeanDefinitions() throws IOException, ClassNotFoundException {
        List<BeanDefinition> beanDefinitions = new ArrayList<>();

        List<BeanDefinition> beanConfigBeanDefinitions = readBeanDefinitions("META-INF/bean.configs", this::createBeanConfigBeanDefinitions);
        beanDefinitions.addAll(beanConfigBeanDefinitions);

        return beanDefinitions;
    }

    private List<BeanDefinition> createSimpleBeanDefinition(URL resource, Class<?> beanType) {
        BeanId beanId = new BeanId(beanType, beanType.getName());
        BeanDeclaration beanDeclaration = new BeanDeclaration(resource, beanId);
        return Collections.singletonList(new ConstructorBeanDefinition(beanDeclaration));
    }

    private List<BeanDefinition> createBeanConfigBeanDefinitions(URL resource, Class<?> beanConfigType) {
        List<BeanDefinition> beanDefinitions = new ArrayList<>();
        beanDefinitions.addAll(createSimpleBeanDefinition(resource, beanConfigType));
        List<BeanDefinition> beanConfigBeanDefinitions = new BeanConfig(resource, beanConfigType, beanConfigType).getBeanDefinitions();
        beanDefinitions.addAll(beanConfigBeanDefinitions);

        return beanDefinitions;
    }

    private List<BeanDefinition> readBeanDefinitions(String resource, BiFunction<URL, Class<?>, List<BeanDefinition>> beanDefintionFactory) throws IOException, ClassNotFoundException {
        List<BeanDefinition> beanDefinitions = new ArrayList<>();
        HashSet<Object> unique = new HashSet<>();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(resource);

        while (resources.hasMoreElements()) {
            URL nextResource = resources.nextElement();

            List<String> lines = readLines(nextResource);
            for (String line : lines) {

                Class<?> type = Class.forName(line);
                List<BeanDefinition> providedBeanDefinitions = beanDefintionFactory.apply(nextResource, type);
                for (BeanDefinition providedBeanDefinition : providedBeanDefinitions) {
                    if (unique.add(providedBeanDefinition.getBeanDeclaration().getId())) {
                        beanDefinitions.add(providedBeanDefinition);
                    }
                }

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
