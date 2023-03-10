package com.link_intersystems.carrental.management.pickup;

public class PickupCarRepositoryMock implements PickupCarRepository {
    private CarPickup carPickup;

    @Override
    public void persist(CarPickup carPickup) {
        this.carPickup = carPickup;
    }

    public CarPickup getCarHandover() {
        return carPickup;
    }
}
