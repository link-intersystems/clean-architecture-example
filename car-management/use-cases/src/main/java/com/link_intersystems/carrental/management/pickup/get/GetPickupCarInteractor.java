package com.link_intersystems.carrental.management.pickup.get;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.rental.CarRental;

class GetPickupCarInteractor implements GetPickupCarUseCase {

    private GetPickupCarRepository getPickupCarRepository;

    public GetPickupCarInteractor(GetPickupCarRepository getPickupCarRepository) {
        this.getPickupCarRepository = getPickupCarRepository;
    }

    @Override
    public GetPickupCarResponseModel getPickupCar(int bookingNumber) {
        CarRental carPickup = getPickupCarRepository.find(new BookingNumber(bookingNumber));
        return toResponseModel(carPickup);
    }

    private static GetPickupCarResponseModel toResponseModel(CarRental carPickup) {
        GetPickupCarResponseModel getPickupCarResponseModel = new GetPickupCarResponseModel();
        getPickupCarResponseModel.setBookingNumber(carPickup.getBookingNumber().getValue());
        getPickupCarResponseModel.setPickupDate(carPickup.getPickupDateTime());
        getPickupCarResponseModel.setFuelLevel(carPickup.getPickupCarState().getFuelLevel());
        getPickupCarResponseModel.setOdometer(carPickup.getPickupCarState().getOdometer().getValue());
        return getPickupCarResponseModel;
    }
}
