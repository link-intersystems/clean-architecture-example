package com.link_intersystems.carrental.management.rental.pickup.list;


import com.link_intersystems.carrental.management.rental.CarRental;

import java.util.ArrayList;
import java.util.List;

class ListPickupCarInteractor implements ListPickupCarUseCase {

    private ListPickupCarRepository listPickupCarRepository;

    public ListPickupCarInteractor(ListPickupCarRepository listPickupCarRepository) {
        this.listPickupCarRepository = listPickupCarRepository;
    }

    @Override
    public ListPickupCarResponseModel listPickedUpCars() {

        List<CarRental> carPickups = listPickupCarRepository.findAll();

        return toResponseModel(carPickups);
    }

    private static ListPickupCarResponseModel toResponseModel(List<CarRental> carRentals) {


        List<PickupCarResponseModel> pickupCarResponseModels = new ArrayList<>();
        for (CarRental carRental : carRentals) {
            PickupCarResponseModel pickupCarResponseModel = new PickupCarResponseModel();
            pickupCarResponseModel.setBookingNumber(carRental.getBookingNumber().getValue());
            pickupCarResponseModel.setPickupDate(carRental.getPickupDateTime());
            pickupCarResponseModels.add(pickupCarResponseModel);
        }

        ListPickupCarResponseModel responseModel = new ListPickupCarResponseModel();

        responseModel.setPickupCars(pickupCarResponseModels);
        return responseModel;
    }
}
