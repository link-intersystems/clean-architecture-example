package com.link_intersystems.carrental.management.rental.pickup.list;


import com.link_intersystems.carrental.components.AOPConfig;

public abstract class ListPickupCarComponent {

    private final AOPConfig aopConfig;

    public ListPickupCarComponent(AOPConfig aopConfig) {
        this.aopConfig = aopConfig;
    }

    public ListPickupCarUseCase getListPickupCarUseCase() {
        ListPickupCarRepository repository = getRepository();
        ListPickupCarInteractor interactor = new ListPickupCarInteractor(repository);
        return aopConfig.applyAOP(ListPickupCarUseCase.class, interactor);
    }

    protected abstract ListPickupCarRepository getRepository();
}
