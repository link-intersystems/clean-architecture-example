package com.link_intersystems.car.booking;

import com.link_intersystems.plugins.AbstractServiceProvider;
import com.link_intersystems.plugins.ApplicationContext;

import javax.sql.DataSource;
import java.util.Set;
import java.util.function.BiConsumer;

public class H2BookingRepositoryServiceProvider extends AbstractServiceProvider {

    @Override
    protected void doInit(ApplicationContext applicationContext, BiConsumer<Class<?>, Object> registerService) {
        DataSource dataSource = applicationContext.getService(DataSource.class);
        registerService.accept(CarBookingRepository.class, new H2CarBookingRepository(dataSource));
    }

    @Override
    protected void initProvidedServiceType(Set<Class<?>> providedServices) {
        providedServices.add(CarBookingRepository.class);
    }
}
