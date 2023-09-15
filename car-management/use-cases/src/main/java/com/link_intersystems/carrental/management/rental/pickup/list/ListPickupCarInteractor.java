package com.link_intersystems.carrental.management.rental.pickup.list;


import com.link_intersystems.carrental.management.rental.CarRental;
import com.link_intersystems.carrental.management.rental.CarState;
import com.link_intersystems.carrental.management.rental.Odometer;

import java.util.ArrayList;
import java.util.List;

class ListPickupCarInteractor implements ListPickupCarUseCase {

    private ListPickupCarRepository listPickupCarRepository;

    public ListPickupCarInteractor(ListPickupCarRepository listPickupCarRepository) {
        this.listPickupCarRepository = listPickupCarRepository;
    }

    private static List<ListPickupCarResponseModel> toResponseModel(List<CarRental> carRentals) {
        List<ListPickupCarResponseModel> pickupCarResponses = new ArrayList<>();

        for (CarRental carRental : carRentals) {
            ListPickupCarResponseModel pickupCarResponse = new ListPickupCarResponseModel();
            pickupCarResponse.setBookingNumber(carRental.getBookingNumber().getValue());
            pickupCarResponse.setPickupDate(carRental.getPickupDateTime());

            CarState pickupCarState = carRental.getPickupCarState();
            Odometer odometer = pickupCarState.getOdometer();
            pickupCarResponse.setOdometer(odometer.getValue());

            pickupCarResponses.add(pickupCarResponse);
        }

        return pickupCarResponses;
    }

    @Override
    public List<ListPickupCarResponseModel> listPickedUpCars() {
        List<CarRental> carPickups = listPickupCarRepository.findAll();

        return toResponseModel(carPickups);
    }
}
