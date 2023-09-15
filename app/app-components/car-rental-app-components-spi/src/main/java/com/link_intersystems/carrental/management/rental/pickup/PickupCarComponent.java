package com.link_intersystems.carrental.management.rental.pickup;

import com.link_intersystems.carrental.DomainEventPublisher;
import com.link_intersystems.carrental.components.AOPConfig;

public abstract class PickupCarComponent {

    private final AOPConfig aopConfig;
    private DomainEventPublisher eventPublisher;

    public PickupCarComponent(AOPConfig aopConfig, DomainEventPublisher eventPublisher) {
        this.aopConfig = aopConfig;
        this.eventPublisher = eventPublisher;
    }

    public PickupCarUseCase getPickupCarUseCase() {
        PickupCarRepository repository = getRepository();
        PickupCarInteractor interactor = new PickupCarInteractor(repository, eventPublisher);
        return aopConfig.applyAOP(PickupCarUseCase.class, interactor);
    }

    protected abstract PickupCarRepository getRepository();
}
