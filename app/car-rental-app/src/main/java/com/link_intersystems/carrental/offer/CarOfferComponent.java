package com.link_intersystems.carrental.offer;


import com.link_intersystems.carrental.main.AOPConfig;

public abstract class CarOfferComponent {

    public AOPConfig aopConfig;

    public CarOfferComponent(AOPConfig aopConfig) {
        this.aopConfig = aopConfig;
    }

    public CarOfferUseCase createCarOfferUseCase() {
        CarOfferRepository repository = getCarOfferRepository();
        CarOfferInteractor carOfferInteractor = new CarOfferInteractor(repository);
        return aopConfig.applyAOP(CarOfferUseCase.class, carOfferInteractor);
    }

    protected abstract CarOfferRepository getCarOfferRepository();
}
