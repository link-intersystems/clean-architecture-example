package com.link_intersystems.carrental.offer;

import com.link_intersystems.plugins.AbstractServiceProvider;
import com.link_intersystems.plugins.ApplicationContext;

import javax.sql.DataSource;
import java.util.Set;
import java.util.function.BiConsumer;

public class H2OfferRepositoryServiceProvider extends AbstractServiceProvider {

    @Override
    protected void doInit(ApplicationContext applicationContext, BiConsumer<Class<?>, Object> registerService) {
        DataSource dataSource = applicationContext.getService(DataSource.class);
        registerService.accept(CarOfferRepository.class, new H2CarOfferRepository(dataSource));
    }

    @Override
    protected void initProvidedServiceType(Set<Class<?>> providedServices) {
        providedServices.add(CarOfferRepository.class);
    }
}
