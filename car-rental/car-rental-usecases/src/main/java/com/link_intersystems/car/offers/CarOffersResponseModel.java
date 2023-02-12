package com.link_intersystems.car.offers;

import com.link_intersystems.car.Car;
import com.link_intersystems.car.Specs;
import com.link_intersystems.money.Amount;
import com.link_intersystems.rental.RentalCar;
import com.link_intersystems.rental.RentalOffer;
import com.link_intersystems.rental.RentalRate;
import com.link_intersystems.time.Period;

import java.util.List;

public class CarOffersResponseModel {

    private CarOffersModel carOffersModel = new CarOffersModel();

    CarOffersResponseModel(List<RentalCar> rentalCars, Period rentalPeriod) {
        for (RentalCar rentalCar : rentalCars) {
            CarOfferModel carOfferModel = map(rentalCar, rentalPeriod);
            carOffersModel.addCarOffer(carOfferModel);
        }
    }

    private CarOfferModel map(RentalCar rentalCar, Period rentalPeriod) {
        CarOfferModel carOfferModel = new CarOfferModel();
        Car car = rentalCar.getCar();
        carOfferModel.setId(car.getId().getValue());
        carOfferModel.setName(car.getName());
        carOfferModel.setVerhicleType(car.getVehicleType().name());

        RentalOffer rentalOffer = rentalCar.getRentalOffer(rentalPeriod);
        Amount totalRentalAmount = rentalOffer.getTotalRentalAmount();
        carOfferModel.setTotalRentalRate(totalRentalAmount.getValue());

        RentalRate rentalRate = rentalCar.getRentalRate();
        carOfferModel.setPerDayRentalRate(rentalRate.getAmount().getValue());

        CarSpecModel carSpecModel = mapCarSpec(car);
        carOfferModel.setCarSpecModel(carSpecModel);

        return carOfferModel;
    }

    private CarSpecModel mapCarSpec(Car car) {
        CarSpecModel carSpecModel = new CarSpecModel();
        Specs specs = car.getSpecs();
        carSpecModel.setSeats(specs.getSeats().getValue());
        carSpecModel.setDoors(specs.getDoors().getValue());
        carSpecModel.setConsumption(specs.getConsumption().getEnergyUnitsPerKm());
        carSpecModel.setEnergyType(specs.getConsumption().getEnergyType().name());
        return carSpecModel;
    }

    public CarOffersModel getCarOffers() {
        return carOffersModel;
    }

}
