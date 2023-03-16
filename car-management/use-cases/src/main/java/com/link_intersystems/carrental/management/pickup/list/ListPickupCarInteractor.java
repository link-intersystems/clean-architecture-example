package com.link_intersystems.carrental.management.pickup.list;

import com.link_intersystems.carrental.management.pickup.CarPickup;

import java.util.ArrayList;
import java.util.List;

class ListPickupCarInteractor implements ListPickupCarUseCase {

    private ListPickupCarRepository listPickupCarRepository;

    public ListPickupCarInteractor(ListPickupCarRepository listPickupCarRepository) {
        this.listPickupCarRepository = listPickupCarRepository;
    }

    @Override
    public ListPickupCarResponseModel listPickedUpCars() {

        List<CarPickup> carPickups = listPickupCarRepository.findAll();

        return toResponseModel(carPickups);
    }

    private static ListPickupCarResponseModel toResponseModel(List<CarPickup> carPickups) {


        List<PickupCarResponseModel> pickupCarResponseModels = new ArrayList<>();
        for (CarPickup carPickup : carPickups) {
            PickupCarResponseModel pickupCarResponseModel = new PickupCarResponseModel();
            pickupCarResponseModel.setBookingNumber(carPickup.getBookingNumber().getValue());
            pickupCarResponseModel.setPickupDate(carPickup.getPickupDateTime());
            pickupCarResponseModels.add(pickupCarResponseModel);
        }

        ListPickupCarResponseModel responseModel = new ListPickupCarResponseModel();

        responseModel.setPickupCars(pickupCarResponseModels);
        return responseModel;
    }
}
