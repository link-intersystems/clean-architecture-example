package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.DomainEventPublisher;
import com.link_intersystems.carrental.main.AOPConfig;

public abstract class CarBookingComponent {

    public AOPConfig aopConfig;

    public CarBookingComponent(AOPConfig aopConfig) {
        this.aopConfig = aopConfig;
    }

    public CarBookingUseCase getCarBookingUseCase(DomainEventPublisher eventPublisher) {
        CarBookingRepository repository = getCarBookingRepository();
        return new CarBookingInteractor(repository, eventPublisher);
    }

    protected abstract CarBookingRepository getCarBookingRepository();
}
