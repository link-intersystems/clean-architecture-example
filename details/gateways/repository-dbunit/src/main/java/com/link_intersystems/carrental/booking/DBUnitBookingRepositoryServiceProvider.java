package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.DataSetRegistry;
import com.link_intersystems.plugins.AbstractServiceProvider;
import com.link_intersystems.plugins.ApplicationContext;

import java.util.Set;
import java.util.function.BiConsumer;

public class DBUnitBookingRepositoryServiceProvider extends AbstractServiceProvider {

    @Override
    protected void doInit(ApplicationContext applicationContext, BiConsumer<Class<?>, Object> registerService) {
        DataSetRegistry dataSetRegistry = applicationContext.getService(DataSetRegistry.class);
        registerService.accept(CarBookingRepository.class, new DBUnitCarBookingRepository(dataSetRegistry));
    }

    @Override
    protected void initProvidedServiceType(Set<Class<?>> providedServices) {
        providedServices.add(CarBookingRepository.class);
    }
}
