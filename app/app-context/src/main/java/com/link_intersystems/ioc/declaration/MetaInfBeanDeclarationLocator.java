package com.link_intersystems.ioc.declaration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;

import static java.util.Objects.*;

public class MetaInfBeanDeclarationLocator implements BeanDeclarationLocator {

    private String configResourceName;
    private List<BeanDeclaration> beanDeclarations;

    public MetaInfBeanDeclarationLocator() {
        this("META-INF/bean.configs");
    }

    public MetaInfBeanDeclarationLocator(String configResourceName) {
        this.configResourceName = requireNonNull(configResourceName);
    }

    @Override
    public List<BeanDeclaration> getBeanDeclarations() {
        if (beanDeclarations == null) {
            try {
                beanDeclarations = readBeanDeclarations(configResourceName);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return beanDeclarations;
    }


    private List<BeanDeclaration> readBeanDeclarations(String resourceName) throws IOException, ClassNotFoundException {
        List<BeanDeclaration> beanDeclarations = new ArrayList<>();
        HashSet<Object> unique = new HashSet<>();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(resourceName);

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();

            List<String> lines = readLines(resource);
            for (String line : lines) {

                Class<?> type = Class.forName(line);
                BeanDeclarationLocation location = new ResourceBeanDeclarationLocation(resource);
                BeanDeclaration beanDeclaration = new BeanDeclaration(type, location);
                if (unique.add(beanDeclaration)) {
                    beanDeclarations.add(beanDeclaration);
                }

            }
        }

        return beanDeclarations;
    }

    private List<String> readLines(URL resource) throws IOException {
        List<String> lines = new ArrayList<>();

        try (InputStream inputStream = resource.openConnection().getInputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                int commentChar = line.indexOf("#");
                if (commentChar > -1) {
                    line = line.substring(0, commentChar);
                }

                if (!line.isBlank()) {
                    lines.add(line);
                }
            }
        }


        return lines;
    }
}
