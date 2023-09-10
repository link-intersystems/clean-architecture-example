package com.link_intersystems.carrental.management.rental.pickup.get;

import com.link_intersystems.carrental.main.AOPConfig;

public abstract class GetPickupCarComponent {

    private final AOPConfig aopConfig;


    public GetPickupCarComponent(AOPConfig aopConfig) {
        this.aopConfig = aopConfig;
    }

    public GetPickupCarUseCase getGetPickupCarUseCase() {
        GetPickupCarRepository getPickupCarRepository = getRepository();
        GetPickupCarInteractor interactor = new GetPickupCarInteractor(getPickupCarRepository);
        return aopConfig.applyAOP(GetPickupCarUseCase.class, interactor);
    }

    protected abstract GetPickupCarRepository getRepository();
}
