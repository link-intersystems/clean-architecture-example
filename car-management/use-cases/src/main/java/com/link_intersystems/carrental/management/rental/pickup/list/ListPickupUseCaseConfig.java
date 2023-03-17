package com.link_intersystems.carrental.management.rental.pickup.list;

public class ListPickupUseCaseConfig {

    public ListPickupCarUseCase getListPickupCarUseCase(ListPickupCarRepository listPickupCarRepository) {
        return new ListPickupCarInteractor(listPickupCarRepository);
    }

}
