package com.link_intersystems.carrental.management.pickup.get;

public class GetPickupUseCaseConfig {

    public GetPickupCarUseCase getGetPickupCarUseCase(GetPickupCarRepository getPickupCarRepository) {
        return new GetPickupCarInteractor(getPickupCarRepository);
    }

}
