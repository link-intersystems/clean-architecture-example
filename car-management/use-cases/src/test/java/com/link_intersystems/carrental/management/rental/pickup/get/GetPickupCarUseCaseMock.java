package com.link_intersystems.carrental.management.rental.pickup.get;

import java.util.HashMap;
import java.util.Map;

public class GetPickupCarUseCaseMock implements GetPickupCarUseCase {

    public static interface GetPickupCarResponse {
        void thenReturn(GetPickupCarResponseModel response);
    }

    private Map<Integer, GetPickupCarResponseModel> getPickupCarResponses = new HashMap<>();

    @Override
    public GetPickupCarResponseModel getPickupCar(int bookingNumber) {
        return getPickupCarResponses.getOrDefault(bookingNumber, new GetPickupCarResponseModel());
    }

    public GetPickupCarResponse whenGetPickupCar(int bookingNumber) {
        return response -> {
            getPickupCarResponses.put(bookingNumber, response);
        };
    }
}