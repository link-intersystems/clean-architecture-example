package com.link_intersystems.carrental.management.rental;

import com.link_intersystems.carrental.DomainEventPublisher;
import com.link_intersystems.carrental.components.AOPConfig;

public abstract class ManagementRentalComponent {

    private final AOPConfig aopConfig;
    private final DomainEventPublisher eventPublisher;


    public ManagementRentalComponent(AOPConfig aopConfig, DomainEventPublisher eventPublisher) {
        this.aopConfig = aopConfig;
        this.eventPublisher = eventPublisher;
    }

    public GetPickupCarUseCase getGetPickupCarUseCase() {
        GetPickupCarRepository getPickupCarRepository = getGetPickupCarRepository();
        GetPickupCarInteractor interactor = new GetPickupCarInteractor(getPickupCarRepository);
        return aopConfig.applyAOP(GetPickupCarUseCase.class, interactor);
    }

    protected abstract GetPickupCarRepository getGetPickupCarRepository();

    public ListPickupCarUseCase getListPickupCarUseCase() {
        ListPickupCarRepository repository = getListPickupCarRepository();
        ListPickupCarInteractor interactor = new ListPickupCarInteractor(repository);
        return aopConfig.applyAOP(ListPickupCarUseCase.class, interactor);
    }

    protected abstract ListPickupCarRepository getListPickupCarRepository();

    public PickupCarUseCase getPickupCarUseCase() {
        PickupCarRepository repository = getPickupCarRepository();
        PickupCarInteractor interactor = new PickupCarInteractor(repository, eventPublisher);
        return aopConfig.applyAOP(PickupCarUseCase.class, interactor);
    }

    protected abstract PickupCarRepository getPickupCarRepository();

    public ReturnCarUseCase getReturnUseCase() {
        ReturnCarRepository repository = getReturnCarRepository();
        ReturnCarInteractor interactor = new ReturnCarInteractor(repository);
        return aopConfig.applyAOP(ReturnCarUseCase.class, interactor);
    }

    protected abstract ReturnCarRepository getReturnCarRepository();
}
