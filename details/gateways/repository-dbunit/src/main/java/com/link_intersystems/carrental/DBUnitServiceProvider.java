package com.link_intersystems.carrental;

import com.link_intersystems.plugins.AbstractServiceProvider;
import com.link_intersystems.plugins.ApplicationContext;

import java.util.Set;
import java.util.function.BiConsumer;

public class DBUnitServiceProvider extends AbstractServiceProvider {

    @Override
    protected void doInit(ApplicationContext applicationContext, BiConsumer<Class<?>, Object> registerService) {
        registerService.accept(DataSetRegistry.class, new DataSetRegistry());
    }

    @Override
    protected void initProvidedServiceType(Set<Class<?>> providedServices) {
        providedServices.add(DataSetRegistry.class);
    }
}
