package com.link_intersystems.car.offers;

import com.link_intersystems.car.Car;

import java.util.List;

public class CarOffersResponseModel {

    private CarOffers carOffers = new CarOffers();

    CarOffersResponseModel(List<Car> cars) {
        for (Car car : cars) {
            CarOffer carOffer = map(car);
            carOffers.addListedFilm(carOffer);
        }
    }

    private CarOffer map(Car car) {
        CarOffer carOffer = new CarOffer();
        carOffer.setId(car.getId().getValue());
        carOffer.setName(car.getName());
        carOffer.setVerhicleType(car.getVehicleType().name());
        return carOffer;
    }

    public CarOffers getCarOffers() {
        return carOffers;
    }

    void setCarOffers(CarOffers carOffers) {
        this.carOffers = carOffers;
    }
}
