package com.link_intersystems.carmanagement.pickup;

class PickupCarInteractor implements PickupCarUseCase {

    private PickupCarRepository pickupCarRepository;

    public PickupCarInteractor(PickupCarRepository pickupCarRepository) {
        this.pickupCarRepository = pickupCarRepository;
    }

    @Override
    public void pickupCar(PickupCarRequestModel pickupCarRequestModel) {

    }
}
