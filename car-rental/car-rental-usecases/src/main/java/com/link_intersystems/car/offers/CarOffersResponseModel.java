package com.link_intersystems.car.offers;

import com.link_intersystems.car.Car;
import com.link_intersystems.money.Amount;
import com.link_intersystems.rental.RentalCar;
import com.link_intersystems.rental.RentalOffer;
import com.link_intersystems.rental.RentalRate;
import com.link_intersystems.time.Period;

import java.util.List;

public class CarOffersResponseModel {

    private CarOffers carOffers = new CarOffers();

    CarOffersResponseModel(List<RentalCar> rentalCars, Period rentalPeriod) {
        for (RentalCar rentalCar : rentalCars) {
            CarOffer carOffer = map(rentalCar, rentalPeriod);
            carOffers.addCarOffer(carOffer);
        }
    }

    private CarOffer map(RentalCar rentalCar, Period rentalPeriod) {
        CarOffer carOffer = new CarOffer();
        Car car = rentalCar.getCar();
        carOffer.setId(car.getId().getValue());
        carOffer.setName(car.getName());
        carOffer.setVerhicleType(car.getVehicleType().name());

        RentalOffer rentalOffer = rentalCar.getRentalOffer(rentalPeriod);
        Amount totalRentalAmount = rentalOffer.getTotalRentalAmount();
        carOffer.setTotalRentalRate(totalRentalAmount.getValue());

        RentalRate rentalRate = rentalCar.getRentalRate();
        carOffer.setPerDayRentalRate(rentalRate.getAmount().getValue());

        return carOffer;
    }

    public CarOffers getCarOffers() {
        return carOffers;
    }

}
