package com.link_intersystems.carrental.booking;

import com.link_intersystems.plugins.AbstractServiceProvider;
import com.link_intersystems.plugins.ApplicationContext;

import java.util.Set;
import java.util.function.BiConsumer;

public class CarBookingServiceProvider extends AbstractServiceProvider {

    @Override
    protected void doInit(ApplicationContext applicationContext, BiConsumer<Class<?>, Object> registerService) {
        CarBookingRepository carBookingRepository = applicationContext.getService(CarBookingRepository.class);
        CarBookingInteractor carBookingInteractor = new CarBookingInteractor(carBookingRepository);
        registerService.accept(CarBookingUseCase.class, carBookingInteractor);
    }

    @Override
    protected void initProvidedServiceType(Set<Class<?>> providedServices) {
        providedServices.add(CarBookingUseCase.class);
    }
}
