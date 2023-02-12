package com.link_intersystems.car.offers;

import com.link_intersystems.car.Car;
import com.link_intersystems.rental.RentalCar;

import java.util.List;

public class CarOffersResponseModel {

    private CarOffers carOffers = new CarOffers();

    CarOffersResponseModel(List<RentalCar> rentalCars) {
        for (RentalCar rentalCar : rentalCars) {
            CarOffer carOffer = map(rentalCar);
            carOffers.addCarOffer(carOffer);
        }
    }

    private CarOffer map(RentalCar rentalCar) {
        CarOffer carOffer = new CarOffer();
        Car car = rentalCar.getCar();
        carOffer.setId(car.getId().getValue());
        carOffer.setName(car.getName());
        carOffer.setVerhicleType(car.getVehicleType().name());
        return carOffer;
    }

    public CarOffers getCarOffers() {
        return carOffers;
    }

}
