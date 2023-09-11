package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.DomainEventPublisher;
import com.link_intersystems.carrental.components.AOPConfig;

public abstract class CarBookingComponent {

    public AOPConfig aopConfig;

    public CarBookingComponent(AOPConfig aopConfig) {
        this.aopConfig = aopConfig;
    }

    public CarBookingUseCase getCarBookingUseCase(DomainEventPublisher eventPublisher) {
        CarBookingRepository repository = getRepository();
        CarBookingInteractor interactor = new CarBookingInteractor(repository, eventPublisher);
        return aopConfig.applyAOP(CarBookingUseCase.class, interactor);
    }

    protected abstract CarBookingRepository getRepository();
}
