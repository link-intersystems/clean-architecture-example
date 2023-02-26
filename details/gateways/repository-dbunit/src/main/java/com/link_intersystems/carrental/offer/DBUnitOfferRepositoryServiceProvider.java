package com.link_intersystems.carrental.offer;

import com.link_intersystems.carrental.DataSetRegistry;
import com.link_intersystems.plugins.AbstractServiceProvider;
import com.link_intersystems.plugins.ApplicationContext;

import javax.sql.DataSource;
import java.util.Set;
import java.util.function.BiConsumer;

public class DBUnitOfferRepositoryServiceProvider extends AbstractServiceProvider {

    @Override
    protected void doInit(ApplicationContext applicationContext, BiConsumer<Class<?>, Object> registerService) {
        DataSetRegistry dataSetRegistry = applicationContext.getService(DataSetRegistry.class);
        registerService.accept(CarOfferRepository.class, new DBUnitCarOfferRepository(dataSetRegistry));
    }

    @Override
    protected void initProvidedServiceType(Set<Class<?>> providedServices) {
        providedServices.add(CarOfferRepository.class);
    }
}
