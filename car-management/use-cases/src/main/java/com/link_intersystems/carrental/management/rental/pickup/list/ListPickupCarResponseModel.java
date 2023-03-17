package com.link_intersystems.carrental.management.rental.pickup.list;

import java.util.List;

public class ListPickupCarResponseModel {
    private List<PickupCarResponseModel> pickupCars;

    void setPickupCars(List<PickupCarResponseModel> pickupCars) {
        this.pickupCars = pickupCars;
    }

    public List<PickupCarResponseModel> getPickupCars() {
        return pickupCars;
    }
}
