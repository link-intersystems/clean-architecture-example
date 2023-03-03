package com.link_intersystems.carrental.pickupcar;

class PickupCarInteractor implements PickupCarUseCase {

    private PickupCarRepository pickupCarRepository;

    public PickupCarInteractor(PickupCarRepository pickupCarRepository) {
        this.pickupCarRepository = pickupCarRepository;
    }

    @Override
    public void pickupCar(PickupCarRequestModel pickupCarRequestModel) {

    }
}
