package com.link_intersystems.carrental.pickupcar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PickupCarInteractorTest {

    private PickupCarInteractor pickupCarInteractor;
    private PickupCarRepositoryMock pickupCarRepositoryMock;

    @BeforeEach
    void setUp() {
        pickupCarRepositoryMock = new PickupCarRepositoryMock();
        pickupCarInteractor = new PickupCarInteractor(pickupCarRepositoryMock);
    }

    @Test
    void pickupCar() {
        PickupCarRequestModel requestModel = new PickupCarRequestModel();
        requestModel.setCarId("");

        pickupCarInteractor.pickupCar(requestModel);
    }
}