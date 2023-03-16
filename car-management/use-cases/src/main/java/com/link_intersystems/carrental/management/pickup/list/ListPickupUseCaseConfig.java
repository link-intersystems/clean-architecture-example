package com.link_intersystems.carrental.management.pickup.list;

public class ListPickupUseCaseConfig {

    public ListPickupCarUseCase getListPickupCarUseCase(ListPickupCarRepository listPickupCarRepository) {
        return new ListPickupCarInteractor(listPickupCarRepository);
    }

}
