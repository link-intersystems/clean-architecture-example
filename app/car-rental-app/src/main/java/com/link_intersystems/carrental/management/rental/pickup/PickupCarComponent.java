package com.link_intersystems.carrental.management.rental.pickup;

import com.link_intersystems.carrental.components.AOPConfig;

public abstract class PickupCarComponent {

    private final AOPConfig aopConfig;

    public PickupCarComponent(AOPConfig aopConfig) {
        this.aopConfig = aopConfig;
    }

    public PickupCarUseCase getPickupCarUseCase() {
        PickupCarRepository repository = getRepository();
        PickupCarInteractor interactor = new PickupCarInteractor(repository);
        return aopConfig.applyAOP(PickupCarUseCase.class, interactor);
    }

    protected abstract PickupCarRepository getRepository();
}
