package com.link_intersystems.carrental.management.pickup;

public class PickupUseCaseConfig {

    public PickupCarUseCase getPickupCarUseCase(PickupCarRepository pickupCarRepository) {
        return new PickupCarInteractor(pickupCarRepository);
    }

}
