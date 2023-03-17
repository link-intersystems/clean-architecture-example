package com.link_intersystems.carrental.management.rental.pickup;

public class PickupUseCaseConfig {

    public PickupCarUseCase getPickupCarUseCase(PickupCarRepository pickupCarRepository) {
        return new PickupCarInteractor(pickupCarRepository);
    }

}
