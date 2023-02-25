package com.link_intersystems.car.offers;

import com.link_intersystems.plugins.AbstractServiceProvider;
import com.link_intersystems.plugins.ApplicationContext;

import javax.sql.DataSource;
import java.util.Set;
import java.util.function.BiConsumer;

public class H2OfferRepositoryServiceProvider extends AbstractServiceProvider {

    @Override
    protected void doInit(ApplicationContext applicationContext, BiConsumer<Class<?>, Object> registerService) {
        DataSource dataSource = applicationContext.getService(DataSource.class);
        registerService.accept(CarOffersRepository.class, new H2CarOfferRepository(dataSource));
    }

    @Override
    protected void initProvidedServiceType(Set<Class<?>> providedServices) {
        providedServices.add(CarOffersRepository.class);
    }
}
