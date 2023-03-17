package com.link_intersystems.carrental.management.rental.pickup.get;

public class GetPickupUseCaseConfig {

    public GetPickupCarUseCase getGetPickupCarUseCase(GetPickupCarRepository getPickupCarRepository) {
        return new GetPickupCarInteractor(getPickupCarRepository);
    }

}
