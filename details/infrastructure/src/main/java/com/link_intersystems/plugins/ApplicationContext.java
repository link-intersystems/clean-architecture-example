package com.link_intersystems.plugins;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public class ApplicationContext {

    private static enum PluginState {
        INITIALIZING,
        INITIALIZED
    }

    private final IdentityHashMap<ServiceProvider, PluginState> pluginStates = new IdentityHashMap<>();
    private final List<ServiceProvider> serviceProviders;

    public ApplicationContext() {
        ServiceLoader<ServiceProvider> pluginLoader = ServiceLoader.load(ServiceProvider.class);
        serviceProviders = pluginLoader.stream().map(ServiceLoader.Provider::get).collect(Collectors.toList());
    }

    public <T> T getService(Class<T> type) {
        for (ServiceProvider serviceProvider : serviceProviders) {
            if (!serviceProvider.provides(type)) {
                continue;
            }

            PluginState pluginState = pluginStates.get(serviceProvider);
            if (pluginState == null) {
                pluginStates.put(serviceProvider, PluginState.INITIALIZING);
                serviceProvider.init(this);
                pluginStates.put(serviceProvider, PluginState.INITIALIZED);
            }

            if (PluginState.INITIALIZING.equals(pluginState)) {
                throw new RuntimeException("Circular service dependency: " + type.getName());
            }

            T service = serviceProvider.getService(type);
            if (service != null) {
                return service;
            }
        }

        throw new RuntimeException("Service " + type.getName() + " is not available.");
    }
}
