package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.DomainEventPublisher;
import com.link_intersystems.carrental.components.AOPConfig;

public abstract class CarBookingComponent {

    public AOPConfig aopConfig;

    public CarBookingComponent(AOPConfig aopConfig) {
        this.aopConfig = aopConfig;
    }

    public CarBookingUseCase getCarBookingUseCase(DomainEventPublisher eventPublisher) {
        CarBookingRepository repository = getCarBookingRepository();
        CarBookingInteractor interactor = new CarBookingInteractor(repository, eventPublisher);
        return aopConfig.applyAOP(CarBookingUseCase.class, interactor);
    }

    public CarOfferUseCase getCarOfferUseCase() {
        CarOfferRepository repository = getCarOfferRepository();
        CarOfferInteractor carOfferInteractor = new CarOfferInteractor(repository);
        return aopConfig.applyAOP(CarOfferUseCase.class, carOfferInteractor);
    }

    protected abstract CarOfferRepository getCarOfferRepository();

    protected abstract CarBookingRepository getCarBookingRepository();
}
