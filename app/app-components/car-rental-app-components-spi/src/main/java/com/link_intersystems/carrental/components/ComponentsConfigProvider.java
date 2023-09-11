package com.link_intersystems.carrental.components;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.ServiceLoader;

public abstract class ComponentsConfigProvider {

    public static ComponentsConfig findComponentsConfig(Properties appProperties) {
        ServiceLoader<ComponentsConfigProvider> providers = ServiceLoader.load(ComponentsConfigProvider.class);

        Iterator<ComponentsConfigProvider> iterator = providers.iterator();

        if (!iterator.hasNext()) {
            throw new IllegalStateException("No " + ComponentsConfigProvider.class.getName() + " found in classpath.");
        }

        ComponentsConfigProvider provider = iterator.next();

        if (iterator.hasNext()) {
            List<? extends Class<? extends ComponentsConfigProvider>> allProviders = providers.stream().map(ServiceLoader.Provider::type).toList();
            throw new IllegalStateException("Multiple " + ComponentsConfigProvider.class.getName() + " found in classpath: " + allProviders);
        }

        return provider.createComponentsConfig(appProperties);
    }


    public abstract ComponentsConfig createComponentsConfig(Properties appProperties);
}
