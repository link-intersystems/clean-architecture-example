package com.link_intersystems.carrental.management.rental.pickup.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListPickupCarUseCaseMock implements ListPickupCarUseCase {

    public static interface ListPickedUpCarsInvocation {
        public void thenReturn(ListPickupCarResponseModel... responseModels);
    }

    private List<ListPickupCarResponseModel> responseModels = new ArrayList<>();

    @Override
    public List<ListPickupCarResponseModel> listPickedUpCars() {
        return responseModels;
    }

    public ListPickedUpCarsInvocation whenListPickupCars() {
        return responseModels -> {
            this.responseModels.clear();
            this.responseModels.addAll(Arrays.asList(responseModels));
        };
    }
}