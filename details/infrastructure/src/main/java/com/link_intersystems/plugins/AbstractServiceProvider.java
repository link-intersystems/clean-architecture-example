package com.link_intersystems.plugins;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public abstract class AbstractServiceProvider implements ServiceProvider {

    private Map<Class<?>, Object> services = new HashMap<>();
    private Set<Class<?>> providedServices;

    @Override
    public void init(ApplicationContext applicationContext) {
        doInit(applicationContext, services::put);
    }

    protected abstract void doInit(ApplicationContext applicationContext, BiConsumer<Class<?>, Object> registerService);

    @Override
    public boolean provides(Class<?> type) {
        return getProvidedServices().contains(type);
    }

    private Set<Class<?>> getProvidedServices() {
        if (providedServices == null) {
            providedServices = new HashSet<>();
            initProvidedServiceType(providedServices);
        }
        return providedServices;
    }

    protected abstract void initProvidedServiceType(Set<Class<?>> providedServices);

    @Override
    public <T> T getService(Class<T> type) {
        return (T) services.get(type);
    }
}
