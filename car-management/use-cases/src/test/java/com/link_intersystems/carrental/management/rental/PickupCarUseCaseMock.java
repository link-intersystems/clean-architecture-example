package com.link_intersystems.carrental.management.rental;

import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PickupCarUseCaseMock implements PickupCarUseCase {

    private List<PickupCarRequestModel> pickupCarInvocations = new ArrayList<>();

    @Override
    public void pickupCar(PickupCarRequestModel pickupCarRequestModel) {
        pickupCarInvocations.add(pickupCarRequestModel);
    }

    public PickupCarUseCase verifyPickupCar(int times) {
        return pickupCarRequestModel -> {
            int frequency = Collections.frequency(pickupCarInvocations, pickupCarRequestModel);
            Assertions.assertEquals(times, frequency, "pickupCar invocations");
        };
    }
}